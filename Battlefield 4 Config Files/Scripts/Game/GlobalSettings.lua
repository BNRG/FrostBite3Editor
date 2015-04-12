-- Global Settings
--
--  These are front-end settings exposed to the end-user. The idea is that
--  settings that are interesting to the majority of end-users should be 
--  defined here to enable tools to query for exposed options.
--
--  This file is imported by Pipeline, Game, etc
--
-- *********************************************************
-- NOTE: This is largely deprecated and will be phased out!
-- *********************************************************

GlobalSettings = {}
GlobalSettings.Meta = {}

local function ValidateSetting(name, value)
	assert(name and value)

	local meta = GlobalSettings.Meta[name]
	local lowerValue = string.lower(value)

	if meta then
		for _,v in ipairs(meta.validValues) do
			if string.lower(v[1]) == lowerValue then
				return v[1]
			end
		end

		-- We intentionally don't build this string in the loop above
		-- in an attempt to generate less ephemeral garbage

		local choices = nil

		for _,v in ipairs(meta.validValues) do
		    if choices then
				choices = choices .. ", " .. v[1]
			else
				choices = v[1]
			end	
		end

		error("Setting '" .. name .. "' cannot be set to specified value: " .. value .. ", valid values are " .. choices)
	else
		error("Undefined setting - '" .. name .. "'")
	end
end

local function DefineSetting(descriptionTable)
	assert(descriptionTable[1] and descriptionTable[2], "Settings must have name and default values!")
	
	local name = descriptionTable[1];
	local defaultValue = descriptionTable[2];
	
	GlobalSettings[name] = defaultValue
	GlobalSettings.Meta[name] = {}

	GlobalSettings.Meta[name].default = defaultValue
	
	if descriptionTable.description then
		GlobalSettings.Meta[name].description = descriptionTable.description
	end

	if descriptionTable.validValues then
		GlobalSettings.Meta[name].validValues = descriptionTable.validValues
	end

	if descriptionTable.commandLine then
		GlobalSettings.Meta[name].commandLine = descriptionTable.commandLine
	end

	if descriptionTable.envVar then
		GlobalSettings.Meta[name].envVar = descriptionTable.envVar
	end
	
	-- Validate default vs validValues
	ValidateSetting(name, defaultValue)
end

function ApplyCommandLineSettings(commandLine)
	for setting,meta in pairs(GlobalSettings.Meta) do
		if meta.commandLine then
			local options = meta.commandLine

			for _,entry in ipairs(options) do
				local k = entry[1]
				local v = entry[2]

				local value = commandLine[string.lower(k)]

				if value then
					if not v then
						SetSetting(setting, value, string.format(" (due to -%s %s)", k, value))
					else
						SetSetting(setting, v, string.format(" (due to -%s)", k))
					end
				end
			end
		end
	end
end

function ApplyEnvironmentVariableSettings()
	for setting,meta in pairs(GlobalSettings.Meta) do
		if meta.envVar then
			local envVarValue = os.getenv(meta.envVar)

			if envVarValue then
				SetSetting(setting, envVarValue)
			end
		end
	end
end


function SetSetting(settingName, value, source)
	if not GlobalSettings[settingName] then 
		error("Attempted set of unknown setting '" .. settingName .. "'")
	else
		GlobalSettings[settingName] = ValidateSetting(settingName, value)

		if log then
			log:debug("Set setting " .. settingName .. " to value: " .. value .. (source or ""))
		end	
	end
end

function GetGlobalSetting(settingName)
	if not GlobalSettings[settingName] then 
		error("Attempted get of unknown setting '" .. settingName .. "'")
	else
		return GlobalSettings[settingName] 
	end
end

--------------------------------------------------------------------------
-- Setting definitions
--

DefineSetting {
	"Cache", "Enabled", 

	validValues = { 
		{ "Enabled", 		"Normal cache mode" },
		{ "Disabled", 		"No caching" },
		{ "ReadOnly", 		"Only read from caches (never upload)" },
		{ "WriteOnly",		"Only write to caches (useful for fixing problems)" }
	},
	description = "Cache Control",
	commandLine = {
		{ "cacheMode" },
		{ "nocache", 		"Disabled" },
		{ "readonlyCache", 	"ReadOnly" },
		{ "writeOnlyCache", "WriteOnly" },
		{ "cache",			"Enabled" }
	},
	envVar = "PIPELINE_CACHE_MODE"
}