function getGpuQuality(gpuName, gpuCount)
    if string.find(gpuName, "GeForce 8") or 
       string.find(gpuName, "GeForce 9") then
        return 1
    elseif string.find(gpuName, "GeForce GTX 26") then
        return 2
    elseif string.find(gpuName, "GeForce GTX 2") or 
		   string.find(gpuName, "ATI Radeon HD 4870 X2") or 
		   string.find(gpuName, "ATI Radeon HD 5800 Series") or 
		   string.find(gpuName, "ATI Radeon HD 5900 Series") or 
		   string.find(gpuName, "AMD Radeon HD 68") or 
		   string.find(gpuName, "AMD Radeon HD 69") or 
		   string.find(gpuName, "GeForce GTX 4") or 
		   string.find(gpuName, "GeForce GTX 5") then
		if gpuCount > 1 then
			return 4
		else
			return 3
		end
    else
        return 1
    end
end

function getCpuQuality(cpuCount, cpuSpeed)
	return (cpuSpeed-1.2)*(cpuCount-1)
end

-- 2 cores, 3.0 gHz : 1.8
-- 2 cores, 3.6 gHz : 2.4
-- 4 cores, 2.0 gHz : 2.4
-- 4 cores, 3.2 gHz : 6
-- 8 cores, 1.8 gHz : 4.2
-- 8 cores, 3.2 gHz : 14!! awesome

vendorDB = {
	[0x1002] = dofile("Scripts/UserOptions/HardwareProfiles/ATI.lua"),
	[0x10DE] = dofile("Scripts/UserOptions/HardwareProfiles/nVidia.lua"),
	[0x8086] = dofile("Scripts/UserOptions/HardwareProfiles/Intel.lua")
}

-- Autodetect settings based on graphics card device id
function getDeviceSettings(vendorId, deviceId, adapterName, isX86)
	if adapterName == "Xenon" then
		return { baseQualityLevel = Quality.Medium, defaultSettings = {}, consoleOverrides = "" }
	elseif adapterName == "PS3" then
		return { baseQualityLevel = Quality.High, defaultSettings = {}, consoleOverrides = "" }
	elseif isX86 == 1 then
		return { baseQualityLevel = Quality.Low, defaultSettings = {}, consoleOverrides = "" }
	else
		return (vendorDB[vendorId] and vendorDB[vendorId][deviceId]) or { baseQualityLevel = Quality.Medium, defaultSettings = {}, consoleOverrides = "" }
	end
end

hardwareSpecs = {
	[0] = {
		cpuCount = detectedCpuCount,
		cpuQuality = getCpuQuality(detectedCpuCount, detectedCpuFreq),
		availableMemory = detectedAvailableMemory,
		adapterName = detectedAdapterName,
		gpuCount = detectedGpuCount,
		adapterQuality = getGpuQuality(detectedAdapterName, detectedGpuCount),
		availableGpuMemory = detectedAvailableGpuMemory,
		deviceSettings = getDeviceSettings(detectedVendorId, detectedDeviceId, detectedAdapterName, isX86)
	},

	[1] = {
		cpuCount = detectedCpuCount,
		cpuQuality = getCpuQuality(2, 2.3),
		availableMemory = 512*1024*1024,
		adapterName = "Faked Low",
		gpuCount = 1,
		adapterQuality = 0,
		availableGpuMemory = 512*1024*1024,
		deviceSettings = { baseQualityLevel = Quality.Low, defaultSettings = {}, consoleOverrides = "" }
	},

	[2] = {
		cpuCount = detectedCpuCount,
		cpuQuality = getCpuQuality(2, 3),
		availableMemory = 1*1024*1024*1024,
		adapterName = "Faked Medium",
		gpuCount = 1,
		adapterQuality = 1,
		availableGpuMemory = 768*1024*1024,
		deviceSettings = { baseQualityLevel = Quality.Medium, defaultSettings = {}, consoleOverrides = "" }
	},

	[3] = {
		cpuCount = detectedCpuCount,
		cpuQuality = getCpuQuality(4, 3),
		availableMemory = 1.5*1024*1024*1024,
		adapterName = "Faked High",
		gpuCount = 1,
		adapterQuality = 3,
		availableGpuMemory = 1*1024*1024*1024,
		deviceSettings = { baseQualityLevel = Quality.High, defaultSettings = {}, consoleOverrides = "" }
	},

	[4] = {
		cpuCount = detectedCpuCount,
		cpuQuality = getCpuQuality(8, 3),
		availableMemory = 2*1024*1024*1024,
		adapterName = "Faked Ultra",
		gpuCount = 2,
		adapterQuality = 4,
		availableGpuMemory = 2*1024*1024*1024,
		deviceSettings = { baseQualityLevel = Quality.Ultra, defaultSettings = {}, consoleOverrides = "" }
	},
}

vendorDB = nil