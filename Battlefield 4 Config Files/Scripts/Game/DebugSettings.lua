core = require "Frost.Core"
logging = require "Frost.Logging"

local function applySettings(settings)
	core.parseKeyValueCfgString(settings, _G) end
	
-- helpers

local function getSetting(key)
	container, field = string.match(key, "(%w+)%.(%w+)");
	if container and field then
		if not _G[container] then
			_G[container] = {}
		end
		return _G[container][field]
	else
		return _G[key]
	end
end

local function setSetting(key, value)
	container, field = string.match(key, "(%w+)%.(%w+)");
	if container and field then
		if not _G[container] then
			_G[container] = {}
		end
		_G[container][field] = value
	else
		_G[key] = value
	end
end

-- thin-client settings: 0=Off 1=Console

setSetting("thin-client", cmdLineOption("thinClient", getSetting("thin-client")))

if getSetting("thin-client") == "1" then
	applySettings([=[
		RenderDevice.NullDriverEnable true
		RenderDevice.CreateMinimalWindow true
		Core.HardwareProfile Hardware_Low
		Core.HardwareGpuBias -1
		DebrisSystem.Enable false
		Client.WorldRenderEnabled false
		Client.EmittersEnabled false
		Client.TerrainEnabled false
		Client.MeshMergingEnabled false
		Client.OvergrowthEnabled false
		Client.OccludersEnabled true
		Decal.SystemEnable false
		Render.DebugRendererEnable false
		UI.HudEnable false
		UI.System UISystem_None		
		EmitterSystem.Enable false
		Window.Fullscreen false
		Render.Enable false
		Render.NullRendererEnable true
		disable-input true
		scripted-input true
		use-script-input true
		disable-client-correction true
		disable-client-animations true
		Sound.Enable false
		disable-entities true
		disable-camera true
		Texture.LoadingEnabled false
		Texture.RenderTexturesEnabled false
		Mesh.LoadingEnabled false
		ShaderSystem.DatabaseLoadingEnable false
		disable-audio-loading true
		GameTime.ForceUseSleepTimer true
		ShaderSystem.DxFrameVertexBufferPoolSize 16
		ShaderSystem.FrameMemoryBufferSize 16
		PerformanceTracker.Enabled false
	]=])
end

-- testingEnabled 0=Off, 1=On

setSetting("testing-enabled", cmdLineOption("testingEnabled", getSetting("testing-enabled")))

if getSetting("testing-enabled") == "1" then
	applySettings([=[
		Server.EnableMonkeyTestLayers true
		add-debug-entity true
		scripted-input true
		use-script-input true
		Juice.logClientTestingEvents true
	]=])
end