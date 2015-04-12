core = require "Frost.Core"
logging = require "Frost.Logging"

levelName = currentGameLevel or cmdLineOption('level') or Game.Level
if levelName then
	-- Level names without slashes are assumed to be shorthand for
	-- Levels/name/name to go with Venice's epic level naming convention
	if not string.find(levelName, "/") then
		levelName = "Levels/" .. levelName .. "/" .. levelName
	end

	Game.FastStartupEnable = true
	if not Game.Level then
		Game.Level = levelName
	end
end


function applySettings(settings)
	core.parseKeyValueCfgString(settings, _G)
end

function applyQualitySettings(name, qualityMap)
	local quality = settings[name]
	if quality == nil then
		log:error("Invalid setting '" .. name .. "'")
		return
	end
	
	local consoleCommands = qualityMap[quality]
	if consoleCommands == nil then
		log:error("Could not find console settings for '" .. name .. "' with quality " .. quality)
	else
		log:info("setting '" .. name .. "': " .. quality)
		applySettings(consoleCommands)
	end
end

dofile("Scripts/UserOptions/Options/Graphics.lua")
dofile("Scripts/UserOptions/Options/Physics.lua")
dofile("Scripts/UserOptions/Options/Sound.lua")
dofile("Scripts/UserOptions/Options/Animations.lua")

applySettings(deviceSettings.consoleOverrides or "")

if not cmdLineOption('noLocalCfg', false) then
	core.readKeyValueCfg("/local/local.cfg", _G)
end

if allowCommandlineSettings then
	for index,option in ipairs(commandLine) do
		local table,member = string.match(option, "^%-(%w+)%.(%w+)")

		if table and member then
			local value = commandLine[index+1] 
			if not _G[table] then _G[table] = {} end
			_G[table][member] = value 
		else
			local varName = string.match(option, "^%-([%w-]+)")
			if varName then
				local value = commandLine[index+1] 
				if value then
					_G[varName] = value
				end
			end
		end
	end
end
