core = require "Frost.Core"
logging = require "Frost.Logging"

function makeConstant(t)
	return setmetatable({}, 
	{
		__index = function (_, n)
			if t[n] == nil then
				log:error("Invalid constant member '" .. tostring(n) .. "'")
			end
			return t[n]
		end,

		__newindex = function (t, n, v)
			log:error("Attempting to change constant " .. tostring(n) .. " to " .. tostring(v))
		end
	})
end

local constants = {
	Quality = makeConstant({
		Autodetect = -1,
		Low = 0,
		Medium = 1,
		High = 2,
		Ultra = 3,
		Custom = 4,
		
		On = 1,
		Off = 0,
	}),

	AnisotropicFilter = makeConstant({
		X1 = 0,
		X2 = 1,
		X4 = 2,
		X8 = 3,
		X16 = 4,
	}),

	AntiAliasingDeferred = makeConstant({
		Off = 0,
		MSAA2X = 1,
		MSAA4X = 2,
	}),

	AntiAliasingPost = makeConstant({
		Off = 0,
		Low = 1,
		Medium = 2,
		High = 3,
	}),

	AmbientOcclusion = makeConstant({
		Off = 0,
		SSAO = 1,
		HBAO = 2,
		HBAOFull = 3,
	}),
	
	MotionBlur = makeConstant({
		Off = 0,
		On = 1,
	}),
}

for k, v in pairs(constants) do
	_G[k] = v
end

dofile("Scripts/UserOptions/HardwareProfiles.lua")
specs = hardwareSpecs[hardwareProfile]
log:info("hardwareProfile : " .. hardwareProfile)
cpuCount = specs['cpuCount']
cpuQuality = specs['cpuQuality'] + hardwareCpuBias
availableMemory = specs['availableMemory']
adapterName = specs['adapterName']
gpuQuality = specs['adapterQuality'] + hardwareGpuBias
gpuCount = specs['gpuCount']
availableGpuMemory = specs['availableGpuMemory']
deviceSettings = specs['deviceSettings']

log:info("cpus : " .. cpuCount)
log:info("memory : " .. availableMemory)
log:info("adapter : " .. adapterName)
log:info("gpu quality : " .. gpuQuality)
log:info("gpuCount : " .. gpuCount)
log:info("gpuMemory : " .. availableGpuMemory)
log:info("isX86 : " .. isX86)


settings = {} 

local function applySettings(settings)
	core.parseKeyValueCfgString(settings, _G) end

defaultSettings = {
	Render = {
		[Quality.Autodetect] = { -- Auto detect, we fill this in when we're done autodetecting
		},

		[Quality.Low] = {
			TextureQuality = Quality.Low,
			TextureFiltering = Quality.Low,
			LightingQuality = Quality.Low,
			EffectsQuality = Quality.Low,
			PostProcessQuality = Quality.Low,
			MeshQuality = Quality.Low,
			TerrainQuality = Quality.Low,
			UndergrowthQuality = Quality.Low,
			
			AntiAliasingDeferred = AntiAliasingDeferred.Off,
			AntiAliasingPost = AntiAliasingPost.Off,
			AnisotropicFilter = AnisotropicFilter.X2,
			AmbientOcclusion = AmbientOcclusion.Off,
			
			MotionBlurEnabled = MotionBlur.Off,
		},

		[Quality.Medium] = {
			TextureQuality = Quality.Medium,
			TextureFiltering = Quality.Medium,
			LightingQuality = Quality.Medium,
			EffectsQuality = Quality.Medium,
			PostProcessQuality = Quality.Medium,
			MeshQuality = Quality.Medium,
			TerrainQuality = Quality.Medium,
			UndergrowthQuality = Quality.Medium,
						
			AntiAliasingDeferred = AntiAliasingDeferred.Off,
			AntiAliasingPost = AntiAliasingPost.Medium,
			AnisotropicFilter = AnisotropicFilter.X4,
			AmbientOcclusion = AmbientOcclusion.SSAO,
			
			MotionBlurEnabled = MotionBlur.Off,
		},

		[Quality.High] = {
			TextureQuality = Quality.High,
			TextureFiltering = Quality.High,
			LightingQuality = Quality.High,
			EffectsQuality = Quality.High,
			PostProcessQuality = Quality.High,
			MeshQuality = Quality.High,
			TerrainQuality = Quality.High,
			UndergrowthQuality = Quality.High,
			
			AntiAliasingDeferred = AntiAliasingDeferred.Off,
			AntiAliasingPost = AntiAliasingPost.High,
			AnisotropicFilter = AnisotropicFilter.X16,
			AmbientOcclusion = AmbientOcclusion.HBAO,
			
			MotionBlurEnabled = MotionBlur.On,
		},
	
		[Quality.Ultra] = {
			TextureQuality = Quality.Ultra,
			TextureFiltering = Quality.Ultra,
			LightingQuality = Quality.Ultra,
			EffectsQuality = Quality.Ultra,
			PostProcessQuality = Quality.Ultra,
			MeshQuality = Quality.Ultra,
			TerrainQuality = Quality.Ultra,
			UndergrowthQuality = Quality.Ultra,
			
			AntiAliasingDeferred = AntiAliasingDeferred.MSAA4X,
			AntiAliasingPost = AntiAliasingPost.High,
			AnisotropicFilter = AnisotropicFilter.X16,
			AmbientOcclusion = AmbientOcclusion.HBAO,
			
			MotionBlurEnabled = MotionBlur.On,
		},
		
		[Quality.Custom] = {
			-- There is a bug in LuaOptionSetManager where it can not handle empty tables, so we add this dummy option here for now (no time to fix the parser atm)
			OverallGraphicsQuality = Quality.Custom + 1
		}
	}
}

settings["OverallGraphicsQuality"] = 0	-- Show as autodetect in UI
for setting, value in pairs(defaultSettings.Render[deviceSettings.baseQualityLevel]) do
	settings[setting] = value
end
for setting, value in pairs(deviceSettings.defaultSettings or {}) do
	settings[setting] = value
end

-- audio thread
if cpuCount < 4 then
	applySettings("Audio.AudioCoreThread 0")
else
	if (cpuCount < 6) then
		applySettings("Audio.AudioCoreThread 2")
	else
		applySettings("Audio.AudioCoreThread "..(cpuCount-1))
	end
end

-- audio
if cpuQuality < 4 then
    settings['AudioQuality'] = 0.0
else
    settings['AudioQuality'] = 1.0
end


-- physics
if cpuQuality < 4 then
    settings['PhysicsQuality'] = 0.0
else
    settings['PhysicsQuality'] = 1.0
end

-- animations
if cpuQuality < 4 then
    settings['AnimationQuality'] = 0.0
else
    settings['AnimationQuality'] = 1.0
end



-- resolution
settings['ResolutionWidth'] = defaultFullscreenWidth or 0
settings['ResolutionHeight'] = defaultFullscreenHeight or 0
settings['FullscreenRefreshRate'] = defaultFullscreenRefreshRate or 0.0
settings['FullscreenScreen'] = 0
settings['FullscreenEnabled'] = defaultFullscreenEnabled or 0
settings['VSyncEnabled'] = defaultVSyncEnabled or 0

-- Test to override auto detected values (using lua to allow use of constants)
--core.readKeyValueCfg("/user/profile/settings/overrideAutodetect.cfg", settings) --will override autodetected values
function vfs_loadfile(filename)
	local f = vfs.open(filename, "r")

	if f == nil then
		return false, "File not found!"
	end
	
	function getNextChunk()
		return f:read(128)
	end
	
	return load(getNextChunk, filename)
end

local overrideFilename = "/user/profile/settings/overrideAutodetect.lua"
local executeAutoDetectOverride, e = vfs_loadfile(overrideFilename)

function bind(f, param)
	return function(...)
		f(param, ...)
	end
end

if executeAutoDetectOverride then
	overrideSettings = setmetatable({},
	{
		__index = constants,
		__newindex = bind(function(s, t, n, v)
			if s[n] == nil then
				log:error("Trying to set unknown setting " .. n .. " to " .. tostring(n))
			elseif v == nil then
				log:error("Trying to set setting " .. n .. " to nil")
			else
				log:info("Overriding " .. n .. ": " .. tostring(v))
				s[n] = v
			end
		end, settings)
	})

	setfenv(executeAutoDetectOverride, overrideSettings)
	local success, e = pcall(executeAutoDetectOverride);
	if not success then
		log:info("Failed to parse '" .. overrideFilename .. "' " .. e)
	end
else
	log:info("Override file error '" .. overrideFilename .. "': " .. e)
end

-- Last thing we do is to save the values we want for the autodetect setting
-- (use the same keys as used for the low settings)
for key, _ in pairs(defaultSettings.Render[Quality.Low]) do
	defaultSettings.Render[Quality.Autodetect][key] = settings[key]
	if settings[key] == nil then
		log:error("Setting " .. key .. " does not have an auto detected default value")
	end
end