-- Pipeline Configuration
--
--   This is an internal script used for configuring the pipeline. Normal
--   users should typically not edit this file, as most end-user settings
--   are exposed through the GlobalSettings.lua file
--

--
-- Import common settings
--

core.import(allScriptsRoot .. "GlobalSettings.lua", "global settings");

-- Import session startup script, if specified on commandLine
-- 
-- This is used by Drone to change settings

if commandLine.startupscript then
	core.import("/data/" .. commandLine.startupScript, _G)
end

core.import(scriptsRoot .. "Settings.lua")

--   -config commandLine switch is used by Drone to configure custom settings
if commandLine.config then
	core.readKeyValueCfg("/data/Scripts/" .. commandLine.config, _G)	
end

--------------------------------------------------------------------------
-- Handle commandline options
--
--   Commandline options effectively override any settings specified
--   elsewhere.
--

local function cmdLineSwitch(switchName, fieldName)
	if (commandLine[string.lower(switchName)]) then
		Pipeline[fieldName] = true
	end
end

local function cmdLineArg(switchName, fieldName)
	local entry = commandLine[string.lower(switchName)]
	
	if (entry) then
		Pipeline[fieldName] = entry
		--print("Set " .. fieldName .. " to " .. entry)
	end
end

cmdLineSwitch("debug", "DebugMode");
cmdLineSwitch("validateData", "ValidateData");
cmdLineSwitch("addToPerforce", "AddToPerforce");
cmdLineSwitch("noconsole", "NoConsole");
cmdLineSwitch("verbose", "Verbose");
cmdLineSwitch("trace", "EnableTracing");
cmdLineSwitch("nohal", "DisableHalDevice");
cmdLineSwitch("trim", "TrimLayout");
cmdLineSwitch("updateIndex", "UpdateIndexOnly");

cmdLineArg("opfilter", "OpFilter")

--
--  Parse commandline options that may occur multiple times ("list" options)
--

----------------------------------------------------------------------------------------
--
-- Diagnostics dump
--

if Pipeline.Verbose then
	print ""
	print "Pipeline settings summary:"
	print "------------------------------------------------------"
	
	for k,v in pairs(Pipeline) do
		print(k, v)
	end
	
	print ""

	print "Command line:"
	print "------------------------------------------------------"
	
	for _,v in pairs(commandLine) do
		print(_,v)
	end

	print ""
end