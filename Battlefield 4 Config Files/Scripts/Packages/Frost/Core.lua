-- Various bits of Lua scaffolding
--

require "os"
require "vfs"

module("Frost.Core", package.seeall);

-- Execute a script
--
--   If flags contains a "silent=true" flag, then no message
--   will be emitted if the script can't be found
--

-- Execute a script, optionally returning an error message if it fails

function import(script, friendlyName, flags)
	local name = friendlyName or script

	local success, err = pcall(dofile,script)
	
	if not success and not (flags and flags.silent) then
		error("Could not import script: " .. name .. " error: " .. err)
	end
end

-- Execute a new-style config file

local function executeCfg(file)
	if log then
		log:debug("  Executing new style (lua) config file")
	end

	local script = file:read("*all")

	local func, err = loadstring(script)

	if func then 
		func() 
	else 
		error(err) 
	end
end

-- Parse a single line containing a key-value pair (and possibly some comments)

function parseKeyValueCfgLine(line, targetTable)
	-- Remove comments

	line = string.gsub(line, "#.*", "")
	line = string.gsub(line, "//.*", "")

	-- Split into key, value pairs
	local key, value = string.match(line, "(%S+)%s+(.+)%s*")

	if key and value then
		-- Handle dotted key names

		local currentTable = targetTable
		local first, second
		second = key
		repeat
			first, second = string.match(second, "([^%.]+)%.?(.*)")

			if first and second then
				if second:len() == 0 then
					currentTable[first] = value
					if log then
						log:debug(string.format("set %s = %s (from key-value cfg line)", key ,value))
					end
				else
					if not currentTable[first] then
						currentTable[first] = {}
					end
					currentTable = currentTable[first]
				end
			end

		until not second or second:len() == 0
	end
end

-- Parse a string of key-value config entries

function parseKeyValueCfgString(str, targetTable)
	for line in string.gmatch(str, "[^\n]*") do
		parseKeyValueCfgLine(line, targetTable)
	end
end

-- Parse an old-style key-value CFG file

function readKeyValueCfg(file, targetTable)
	if log then
		log:debug("Reading cfg '" .. file .. "'")
	end

	local f = vfs.open(file, "r")
	
	if not f then
		-- File not found
		if log then
			log:debug("Failed to find file '" .. file .. "'")
		end
			
		return
	end

	local firstLine = true

	while true do
		local line = f:read("*line")
		
		if not line then 
			break 
		end

		if firstLine and line == "#!/bin/lua" then
			executeCfg(f)
		end
		
		parseKeyValueCfgLine(line, targetTable)
	end
	
	f:close();
end