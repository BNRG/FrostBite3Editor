-- Game configuration script
--

-- Local helper functions
function commandLine:lookup(name, default)
	local value = self[string.lower(name)]

	return value or default
end

local function cmdLineSwitch(switchName, fieldName, inTable)
	local table = inTable or Game

	if (commandLine:lookup(switchName)) then
		table[fieldName] = true
	end
end

function cmdLineOption(optionName, default)
	return commandLine:lookup(optionName, default)
end

local function readCfg(name)
	log:debug("reading cfg: " .. name)
	core.readKeyValueCfg(name, _G)
end

local function applySettings(settings)
	core.parseKeyValueCfgString(settings, _G)
end

-- Basic settings 

core.import("Scripts/Game/Settings.lua")

-- Import old-school cfg files for backwards compatibility
local defaultConfigFile = commandLine:lookup("defaultConfig")
if defaultConfigFile then
	readCfg(defaultConfigFile)
else
	readCfg("Scripts/Game.cfg")
end

--------------------------------------------------------------------------
-- Handle any Drone override options
--

for i,v in ipairs(commandLine) do
	if string.lower(v) == "-customconfig" then
		if commandLine[i+1]:find("/") then
			readCfg(commandLine[i+1])
		else
			readCfg("Scripts/" .. commandLine[i+1])
		end
	end
end

local editorActive = false
local liveEditingEnable = commandLine:lookup("core.liveeditingenable")
if liveEditingEnable then
	if liveEditingEnable == "1" or liveEditingEnable == "true" then
		editorActive = true
	end
end

--------------------------------------------------------------------------
-- Handle local overrides
--
-- This needs to run before we load the level cfg as we can specify the fast startup level name here!

if not commandLine:lookup('noLocalCfg', false) then
	-- Read common local cfg file
	readCfg("/local/local.cfg", _G)

	if editorActive == true then
		-- Read local cfg with editor specific settings
		readCfg("/local/localeditor.cfg", _G)
	else
		-- Read local cfg with standalone specific settings
		readCfg("/local/localgame.cfg", _G)
	end
end

--------------------------------------------------------------------------
-- Handle level specific configuration
--

local levelName = commandLine:lookup("level")
if not levelName then
	local gameTable = _G["Game"]
	if gameTable then
		levelName = gameTable["Level"]
	end
end

-- Level names without slashes are assumed to be shorthand for
-- Levels/[(SP|MP)/]name/name to go with Warsaw's epic level naming convention
if levelName and not string.find(levelName, "/") then
	if string.len(levelName) > 2 and (string.upper(string.sub(levelName, 1, 2)) == "SP" or string.upper(string.sub(levelName, 1, 2)) == "MP") then
		levelName = "Levels/" .. string.sub(levelName, 1, 2) .. "/" .. levelName .. "/" .. levelName
	else
		levelName = "Levels/" .. levelName .. "/" .. levelName
	end
	_G["Game"]["Level"] = levelName
end

if levelName then
	readCfg(levelName .. ".cfg")
end

--------------------------------------------------------------------------
-- Handle commandline options
--
--   Commandline options effectively override any settings specified
--   elsewhere.
--

if not Game then Game = {} end

cmdLineSwitch("verbose", "Verbose")

if Game.Verbose then
	print "------------Commandline parameters:"
	for k,v in pairs(commandLine) do print (k,v) end
end

-- Handle <Table>.<Member> settings

if allowCommandlineSettings then
	for _,option in ipairs(commandLine) do
		local table,member = string.match(option, "^%-(%w+)%.(%w+)")

		if table and member then
			local value = commandLine[_ + 1] 
			if not _G[table] then _G[table] = {} end
			_G[table][member] = value 
		else
			local varName = string.match(option, "^%-([%w-]+)")
			if varName then
				local value = commandLine[_ + 1] 
				if value then
					_G[varName] = value
				end
			end
		end
		
	end
end

--------------------------------------------------------------------------
-- Force specific settings when running through FrostEd
--

if editorActive then
	Online.Backend = "Backend_Lan"
	Online.UseFallback = true
	Online.ClientIsPresenceEnabled = false
	Online.ServerIsPresenceEnabled = false

	applySettings([=[
		Server.forceStartMapOnLoad true
		Network.GhostCountWarning true
		Network.GhostCountWarningInfo true
		Game.DisablePreRound true
	]=])
end

--

core.import("Scripts/Game/DebugSettings.lua")