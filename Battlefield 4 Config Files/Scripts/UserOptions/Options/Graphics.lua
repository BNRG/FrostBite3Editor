------ Common settings for all quality levels
applySettings([=[
]=])


------ Texture Quality
-- ShaderQuality level: This should really be its own user setting
applyQualitySettings('TextureQuality', {

	[Quality.Low]=[=[
		Texture.SkipMipmapCount 1
		TextureStreaming.PoolSize 230000
		TerrainStreaming.HeightfieldAtlasSampleCountXFactor 1
		TerrainStreaming.HeightfieldAtlasSampleCountYFactor 1
		TerrainStreaming.MaskAtlasSampleCountXFactor 1
		TerrainStreaming.MaskAtlasSampleCountYFactor 1
		TerrainStreaming.ColorAtlasSampleCountXFactor 1
		TerrainStreaming.ColorAtlasSampleCountYFactor 1
		VisualTerrain.TextureAtlasSampleCountXFactor 1
		VisualTerrain.TextureAtlasSampleCountYFactor 1
		VisualTerrain.TextureRenderJobCount 1
		VisualTerrain.TextureRenderJobsLaunchedPerFrameCountMax 1
		VisualTerrain.TextureSkipMipSpeed 20
		VisualTerrain.Decal3dFarDrawDistanceScaleFactor 1.0
		
		WorldRender.SkyEnvmapResolution 128
		WorldRender.DynamicEnvmapResolution 128
		
		ShaderSystem.ShaderQualityLevel QualityLevel_Low
	]=],

	-- 768 - 1 GB
	[Quality.Medium]=[=[
		Texture.SkipMipmapCount 1
		TextureStreaming.PoolSize 300000
		TerrainStreaming.HeightfieldAtlasSampleCountXFactor 1
		TerrainStreaming.HeightfieldAtlasSampleCountYFactor 2
		TerrainStreaming.MaskAtlasSampleCountXFactor 1
		TerrainStreaming.MaskAtlasSampleCountYFactor 2
		TerrainStreaming.ColorAtlasSampleCountXFactor 1
		TerrainStreaming.ColorAtlasSampleCountYFactor 2
		VisualTerrain.TextureAtlasSampleCountXFactor 1
		VisualTerrain.TextureAtlasSampleCountYFactor 2
		VisualTerrain.TextureRenderJobCount 1
		VisualTerrain.TextureRenderJobsLaunchedPerFrameCountMax 1
		VisualTerrain.TextureSkipMipSpeed 20
		VisualTerrain.Decal3dFarDrawDistanceScaleFactor 1.2
		
		WorldRender.SkyEnvmapResolution 256
		WorldRender.DynamicEnvmapResolution 256
		
		ShaderSystem.ShaderQualityLevel QualityLevel_Medium
	]=],
	
	-- 1 - 2 GB
	[Quality.High]=[=[
		Texture.SkipMipmapCount 0
		TextureStreaming.PoolSize 450000
		TerrainStreaming.HeightfieldAtlasSampleCountXFactor 1
		TerrainStreaming.HeightfieldAtlasSampleCountYFactor 2
		TerrainStreaming.MaskAtlasSampleCountXFactor 1
		TerrainStreaming.MaskAtlasSampleCountYFactor 2
		TerrainStreaming.ColorAtlasSampleCountXFactor 1
		TerrainStreaming.ColorAtlasSampleCountYFactor 2
		VisualTerrain.TextureAtlasSampleCountXFactor 1
		VisualTerrain.TextureAtlasSampleCountYFactor 2
		VisualTerrain.TextureRenderJobCount 2
		VisualTerrain.TextureRenderJobsLaunchedPerFrameCountMax 2
		VisualTerrain.TextureSkipMipSpeed 30
		VisualTerrain.Decal3dFarDrawDistanceScaleFactor 1.7
		
		WorldRender.SkyEnvmapResolution 512
		WorldRender.DynamicEnvmapResolution 256
		
		ShaderSystem.ShaderQualityLevel QualityLevel_High
	]=],

	-- 2 Gb or more
	[Quality.Ultra]=[=[
		Texture.SkipMipmapCount 0
		TextureStreaming.PoolSize 750000
		TerrainStreaming.HeightfieldAtlasSampleCountXFactor 2
		TerrainStreaming.HeightfieldAtlasSampleCountYFactor 2
		TerrainStreaming.MaskAtlasSampleCountXFactor 2
		TerrainStreaming.MaskAtlasSampleCountYFactor 2
		TerrainStreaming.ColorAtlasSampleCountXFactor 2
		TerrainStreaming.ColorAtlasSampleCountYFactor 2
		VisualTerrain.TextureAtlasSampleCountXFactor 2
		VisualTerrain.TextureAtlasSampleCountYFactor 2
		VisualTerrain.TextureRenderJobCount 3
		VisualTerrain.TextureRenderJobsLaunchedPerFrameCountMax 3
		VisualTerrain.TextureSkipMipSpeed 40
		VisualTerrain.Decal3dFarDrawDistanceScaleFactor 2
		
		WorldRender.SkyEnvmapResolution 512
		WorldRender.DynamicEnvmapResolution 512
		
		ShaderSystem.ShaderQualityLevel QualityLevel_Ultra
	]=],
})

------ Texture filtering Quality
applyQualitySettings('TextureFiltering', {
	[Quality.Low]=[=[
		ShaderSystem.MaxAnisotropyLow 1
		ShaderSystem.MaxAnisotropyMedium 1
		ShaderSystem.MaxAnisotropyHigh 2
		ShaderSystem.MaxAnisotropyUltra 4
	]=],
	[Quality.Medium]=[=[
		ShaderSystem.MaxAnisotropyLow 1
		ShaderSystem.MaxAnisotropyMedium 2
		ShaderSystem.MaxAnisotropyHigh 4
		ShaderSystem.MaxAnisotropyUltra 4
	]=],
	[Quality.High]=[=[
		ShaderSystem.MaxAnisotropyLow 2
		ShaderSystem.MaxAnisotropyMedium 4
		ShaderSystem.MaxAnisotropyHigh 4
		ShaderSystem.MaxAnisotropyUltra 8
	]=],
	[Quality.Ultra]=[=[
		ShaderSystem.MaxAnisotropyLow 4
		ShaderSystem.MaxAnisotropyMedium 8
		ShaderSystem.MaxAnisotropyHigh 8
		ShaderSystem.MaxAnisotropyUltra 16
	]=],
})

------ Mesh Quality
applyQualitySettings('MeshQuality', {
	[Quality.Low]=[=[
		WorldRender.CullScreenAreaScale 1
		Mesh.GlobalLodScale 1
		Mesh.ShadowDistanceScale 1
		Mesh.TessellationEnable 0
		MeshStreaming.PoolSize 135000
		
		VegetationSystem.MaxActiveDistance 100
		VegetationSystem.MaxPreSimsPerJob 2
		VegetationSystem.SimulationMemKbClient 2048
		
		Render.EdgeModelViewDistance 50
		
		Decal.RingBufferMaxVertexCount 8192
		Decal.StaticBufferMaxVertexCount 16384

		WaterInteract.WaterQualityLevel Low
	]=],
	[Quality.Medium]=[=[
		WorldRender.CullScreenAreaScale 1.25
		Mesh.GlobalLodScale 1.25
		Mesh.ShadowDistanceScale 1.5
		Mesh.TessellationEnable 0
		MeshStreaming.PoolSize 175000
		
		VegetationSystem.MaxActiveDistance 200
		VegetationSystem.MaxPreSimsPerJob 3
		VegetationSystem.SimulationMemKbClient 2048
		
		Decal.RingBufferMaxVertexCount 8192
		Decal.StaticBufferMaxVertexCount 16384
		
		Render.EdgeModelViewDistance 70
		Tessellation.Enable 0

		WaterInteract.WaterQualityLevel Medium
	]=],
	[Quality.High]=[=[
		WorldRender.CullScreenAreaScale 1.5
		Mesh.GlobalLodScale 1.5
		Mesh.ShadowDistanceScale 2.0
		Mesh.TessellationEnable 1
		Mesh.TessellationMaxFactor 7
		MeshStreaming.PoolSize 200000
		
		VegetationSystem.MaxActiveDistance 300
		VegetationSystem.MaxPreSimsPerJob 4
		VegetationSystem.SimulationMemKbClient 4096
		
		Render.EdgeModelViewDistance 100

		Decal.RingBufferMaxVertexCount 12288
		Decal.StaticBufferMaxVertexCount 32768
		
		WaterInteract.WaterQualityLevel High
	]=],
	[Quality.Ultra]=[=[
		WorldRender.CullScreenAreaScale 2
		Mesh.GlobalLodScale 1.5
		Mesh.ShadowDistanceScale 10
		Mesh.TessellationEnable 1
		Mesh.TessellationMaxFactor 15
		MeshStreaming.PoolSize 250000
		
		VegetationSystem.MaxActiveDistance 350
		VegetationSystem.MaxPreSimsPerJob 8
		VegetationSystem.SimulationMemKbClient 4096
		
		Render.EdgeModelViewDistance 150

		Decal.RingBufferMaxVertexCount 12288
		Decal.StaticBufferMaxVertexCount 32768
		
		WaterInteract.WaterQualityLevel Ultra
	]=],
})



-- can't use High or Medium quality on DX10 as QuadMaxCount is above 8192 which is the max size for 1d textures
-- this is a workaround for bug http://frostbite.ea.com:8080/browse/WAR-40148
if dx10 == 1 and (settings['EffectsQuality'] == Quality.High or settings['EffectsQuality'] == Quality.Ultra) then
	log:info("clamping effects quality to medium due to DX10 limitations")
	settings['EffectsQuality'] = Quality.Medium
end

------ Effects Quality
-- On Ultra, force transparent surfaces to be rendered at full res in order to conserve transparent 
-- sorting with full res. particles
applyQualitySettings('EffectsQuality', {
	[Quality.Low]=[=[
		DynamicTextureAtlas.EmitterBaseWidth 4096
		DynamicTextureAtlas.EmitterBaseHeight 4096
		DynamicTextureAtlas.EmitterBaseMipmapCount 5
		DynamicTextureAtlas.EmitterBaseSkipmipsCount 1
		DynamicTextureAtlas.EmitterNormalWidth 1024
		DynamicTextureAtlas.EmitterNormalHeight 1024
		DynamicTextureAtlas.EmitterNormalMipmapCount 3
		DynamicTextureAtlas.EmitterNormalSkipmipsCount 1
		EmitterSystem.QuadHalfResEnable 1
		EmitterSystem.QuadMaxCount 6000
		EmitterSystem.MeshMaxCount 2000
		EmitterSystem.CollisionRayCastMaxCount 50
		EmitterSystem.EmitterQualityLevel Low
		EffectSystem.EffectQualityLevel Low
		WorldRender.HalfResLensFlaresEnable 1
	]=],
	[Quality.Medium]=[=[
		DynamicTextureAtlas.EmitterBaseWidth 4096
		DynamicTextureAtlas.EmitterBaseHeight 4096
		DynamicTextureAtlas.EmitterBaseMipmapCount 5
		DynamicTextureAtlas.EmitterBaseSkipmipsCount 1
		DynamicTextureAtlas.EmitterNormalWidth 1024
		DynamicTextureAtlas.EmitterNormalHeight 1024
		DynamicTextureAtlas.EmitterNormalMipmapCount 3
		DynamicTextureAtlas.EmitterNormalSkipmipsCount 1
		EmitterSystem.QuadHalfResEnable 1
		EmitterSystem.QuadMaxCount 8000
		EmitterSystem.MeshMaxCount 3000
		EmitterSystem.CollisionRayCastMaxCount 100
		EmitterSystem.EmitterQualityLevel Medium
		EffectSystem.EffectQualityLevel Medium
		WorldRender.HalfResLensFlaresEnable 1
	]=],
	[Quality.High]=[=[
		DynamicTextureAtlas.EmitterBaseWidth 8192
		DynamicTextureAtlas.EmitterBaseHeight 8192
		DynamicTextureAtlas.EmitterBaseMipmapCount 6
		DynamicTextureAtlas.EmitterBaseSkipmipsCount 0
		DynamicTextureAtlas.EmitterNormalWidth 2048
		DynamicTextureAtlas.EmitterNormalHeight 2048
		DynamicTextureAtlas.EmitterNormalMipmapCount 4
		DynamicTextureAtlas.EmitterNormalSkipmipsCount 0
		EmitterSystem.QuadHalfResEnable 1
		EmitterSystem.QuadMaxCount 12000
		EmitterSystem.MeshMaxCount 4000
		EmitterSystem.CollisionRayCastMaxCount 150
		EmitterSystem.EmitterQualityLevel High
		EffectSystem.EffectQualityLevel High
		WorldRender.HalfResLensFlaresEnable 0
	]=],
	[Quality.Ultra]=[=[
		DynamicTextureAtlas.EmitterBaseWidth 8192
		DynamicTextureAtlas.EmitterBaseHeight 8192
		DynamicTextureAtlas.EmitterBaseMipmapCount 6
		DynamicTextureAtlas.EmitterBaseSkipmipsCount 0
		DynamicTextureAtlas.EmitterNormalWidth 2048
		DynamicTextureAtlas.EmitterNormalHeight 2048
		DynamicTextureAtlas.EmitterNormalMipmapCount 4
		DynamicTextureAtlas.EmitterNormalSkipmipsCount 0
		EmitterSystem.QuadHalfResEnable 0
		EmitterSystem.QuadMaxCount 16000
		EmitterSystem.MeshMaxCount 8000
		EmitterSystem.CollisionRayCastMaxCount 250
		EmitterSystem.EmitterQualityLevel Ultra
		EffectSystem.EffectQualityLevel Ultra

		WorldRender.HalfResLensFlaresEnable 0
		WorldRender.ForceFullResEnable 1
	]=],
})

------ PostProcess Quality
applyQualitySettings('PostProcessQuality', {
	[Quality.Low]=[=[
		PostProcess.SpriteDofEnable 0
		PostProcess.DofMethod DofMethod_Gaussian
		PostProcess.BlurMethod BlurMethod_Gaussian
		PostProcess.BloomQuarterResEnable 1		
		WorldRender.DistortionEnable 0
		WorldRender.MotionBlurEnable 0
		WorldRender.FastHdrEnable 1
		WorldRender.RenderScaleResampleMode ScaleResampleMode_Linear
	]=],
	[Quality.Medium]=[=[
		PostProcess.SpriteDofEnable 0
		PostProcess.DofMethod DofMethod_Gaussian
		PostProcess.BlurMethod BlurMethod_Gaussian
		PostProcess.BloomQuarterResEnable 0
		WorldRender.DistortionEnable 1
		WorldRender.MotionBlurEnable 0
		WorldRender.FastHdrEnable 1
		WorldRender.RenderScaleResampleMode ScaleResampleMode_BicubicSharp
	]=],
	[Quality.High]=[=[
		PostProcess.SpriteDofEnable 1
		PostProcess.DofMethod DofMethod_Sprite
		PostProcess.BlurMethod BlurMethod_Sprite
		PostProcess.BloomQuarterResEnable 0
		WorldRender.DistortionEnable 1
		WorldRender.MotionBlurEnable 1
		WorldRender.MotionBlurMaxSampleCount 16
		WorldRender.FastHdrEnable 0
		WorldRender.RenderScaleResampleMode ScaleResampleMode_BicubicSharp
	]=],
	[Quality.Ultra]=[=[
		PostProcess.SpriteDofEnable 1
		PostProcess.DofMethod DofMethod_Sprite
		PostProcess.BlurMethod BlurMethod_Sprite
		PostProcess.BloomQuarterResEnable 0
		WorldRender.DistortionEnable 1
		WorldRender.MotionBlurEnable 1
		WorldRender.MotionBlurMaxSampleCount 20
		WorldRender.FastHdrEnable 0
		WorldRender.RenderScaleResampleMode ScaleResampleMode_BicubicSharp
	]=],
})


------ Terrain Quality
applyQualitySettings('TerrainQuality', {
	[Quality.Low]=[=[
		VisualTerrain.DxDisplacementMappingEnable 0
		VisualTerrain.DxTessellatedTriWidth 12
		VisualTerrain.LodScale 1.0
	]=],
	[Quality.Medium]=[=[
		VisualTerrain.DxDisplacementMappingEnable 0
		VisualTerrain.DxTessellatedTriWidth 12
		VisualTerrain.LodScale 1.1
	]=],
	-- Only on DX11 cards
	[Quality.High]=[=[
		VisualTerrain.DxDisplacementMappingEnable 1
		VisualTerrain.DxTessellatedTriWidth 12
		VisualTerrain.LodScale 1.1
	]=],
	[Quality.Ultra]=[=[
		VisualTerrain.DxDisplacementMappingEnable 1
		VisualTerrain.DxTessellatedTriWidth 9
		VisualTerrain.LodScale 1.1
	]=],
})


------ Terrain Decoration Quality
applyQualitySettings('UndergrowthQuality', {
	[Quality.Low]=[=[
		VisualTerrain.MeshScatteringBuildChannelCount 2
		VisualTerrain.MeshScatteringBuildChannelsLaunchedPerFrameCountMax 1
		VisualTerrain.MeshScatteringDensityScaleFactor 1
		VisualTerrain.MeshScatteringDistanceScaleFactor 1
		VisualTerrain.MeshScatteringInstancesPerCellMax 2048
		VisualTerrain.MeshScatteringQualityLevel Low
	]=],
	[Quality.Medium]=[=[
		VisualTerrain.MeshScatteringBuildChannelCount 4
		VisualTerrain.MeshScatteringBuildChannelsLaunchedPerFrameCountMax 2
		VisualTerrain.MeshScatteringDistanceScaleFactor 1.25
		VisualTerrain.MeshScatteringDensityScaleFactor 1
		VisualTerrain.MeshScatteringInstancesPerCellMax 3072
		VisualTerrain.MeshScatteringQualityLevel Medium
	]=],
	[Quality.High]=[=[
		VisualTerrain.MeshScatteringBuildChannelCount 6
		VisualTerrain.MeshScatteringBuildChannelsLaunchedPerFrameCountMax 4
		VisualTerrain.MeshScatteringDistanceScaleFactor 1.5
		VisualTerrain.MeshScatteringDensityScaleFactor 1
		VisualTerrain.MeshScatteringInstancesPerCellMax 4096
		VisualTerrain.MeshScatteringQualityLevel High
	]=],
	[Quality.Ultra]=[=[
		VisualTerrain.MeshScatteringBuildChannelCount 8
		VisualTerrain.MeshScatteringBuildChannelsLaunchedPerFrameCountMax 4
		VisualTerrain.MeshScatteringDistanceScaleFactor 2
		VisualTerrain.MeshScatteringDensityScaleFactor 1
		VisualTerrain.MeshScatteringInstancesPerCellMax 4096
		VisualTerrain.MeshScatteringQualityLevel Ultra
	]=],
})


------ Light quality (previously ShadowQuality)
applyQualitySettings('LightingQuality', {
	[Quality.Low]=[=[
		WorldRender.ShadowmapsEnable true
		WorldRender.ShadowmapViewDistance 140
		WorldRender.ShadowmapResolution 700
		WorldRender.ShadowmapSliceCount 3
		WorldRender.ShadowmapQuality 0
		WorldRender.TransparencyShadowmapsEnable 0
		WorldRender.SpotLightShadowmapLevel QualityLevel_Low
		WorldRender.SpotLightsAsConeLightsLevel QualityLevel_Low
		Mesh.CastSunShadowQualityLevel Low
		Mesh.CastDynamicEnvmapQualityLevel Low
		Mesh.CastPlanarReflectionQualityLevel Low
		VegetationSystem.UseShadowLodOffset 1
		WorldRender.SubSurfaceScatteringEnable 0
		Enlighten.CubeMapsEnable 0
	]=],
	[Quality.Medium]=[=[
		WorldRender.ShadowmapsEnable true
		WorldRender.ShadowmapViewDistance 140
		WorldRender.ShadowmapResolution 896
		WorldRender.ShadowmapSliceCount 4
		WorldRender.ShadowmapQuality 1
		WorldRender.TransparencyShadowmapsEnable 0
		WorldRender.SpotLightShadowmapLevel QualityLevel_Medium
		WorldRender.SpotLightsAsConeLightsLevel QualityLevel_Medium
		Mesh.CastSunShadowQualityLevel Medium
		Mesh.CastDynamicEnvmapQualityLevel Medium
		Mesh.CastPlanarReflectionQualityLevel Medium
		VegetationSystem.UseShadowLodOffset 1
		WorldRender.SubSurfaceScatteringEnable 0
		Enlighten.CubeMapsEnable 0
	]=],
	[Quality.High]=[=[
		WorldRender.ShadowmapsEnable true
		WorldRender.ShadowmapViewDistance 200
		WorldRender.ShadowmapResolution 1296
		WorldRender.ShadowmapSliceCount 4
		WorldRender.ShadowmapQuality 1
		WorldRender.TransparencyShadowmapsEnable 1
		WorldRender.SpotLightShadowmapLevel QualityLevel_High
		WorldRender.SpotLightsAsConeLightsLevel QualityLevel_High
		Mesh.CastSunShadowQualityLevel High
		Mesh.CastDynamicEnvmapQualityLevel High
		Mesh.CastPlanarReflectionQualityLevel High
		VegetationSystem.UseShadowLodOffset 0
		WorldRender.SubSurfaceScatteringEnable 1
		Enlighten.CubeMapsEnable 1
	]=],
	[Quality.Ultra]=[=[
		WorldRender.ShadowmapsEnable true
		WorldRender.ShadowmapViewDistance 250
		WorldRender.ShadowmapResolution 1600
		WorldRender.ShadowmapSliceCount 4
		WorldRender.ShadowmapQuality 1
		WorldRender.TransparencyShadowmapsEnable 1
		WorldRender.SpotLightShadowmapLevel QualityLevel_Ultra
		WorldRender.SpotLightsAsConeLightsLevel QualityLevel_Ultra
		Mesh.CastSunShadowQualityLevel Ultra
		Mesh.CastDynamicEnvmapQualityLevel Ultra
		Mesh.CastPlanarReflectionQualityLevel Ultra
		VegetationSystem.UseShadowLodOffset 0
		WorldRender.SubSurfaceScatteringEnable 1
		Enlighten.CubeMapsEnable 1
	]=],
})


------ Antialiasing Deferred
applyQualitySettings('AntiAliasingDeferred', {
	[AntiAliasingDeferred.Off]=[=[
		WorldRender.MultisampleCount 1
	]=],
	[AntiAliasingDeferred.MSAA2X]=[=[
		WorldRender.MultisampleCount 2
	]=],
	[AntiAliasingDeferred.MSAA4X]=[=[
		WorldRender.MultisampleCount 4
	]=],
})

	
------ Antialiasing Post
applyQualitySettings('AntiAliasingPost', {
	[AntiAliasingPost.Off]=[=[
		WorldRender.PostProcessAntialiasingMode PostProcessAAMode_None
	]=],
	[AntiAliasingPost.Low]=[=[
		WorldRender.PostProcessAntialiasingMode PostProcessAAMode_FxaaLow
	]=],
	[AntiAliasingPost.Medium]=[=[
		WorldRender.PostProcessAntialiasingMode PostProcessAAMode_FxaaMedium
	]=],
	[AntiAliasingPost.High]=[=[
		WorldRender.PostProcessAntialiasingMode PostProcessAAMode_FxaaHigh
	]=],
})


------ Ambient Occlusion
applyQualitySettings('AmbientOcclusion', {
	[AmbientOcclusion.Off]=[=[
		PostProcess.DynamicAOEnable 0
		PostProcess.HbaoHalfResEnable 1
	]=],
	[AmbientOcclusion.SSAO]=[=[
		PostProcess.DynamicAOEnable 1
		PostProcess.DynamicAOMethod DynamicAOMethod_SSAO
		PostProcess.HbaoHalfResEnable 1
	]=],
	[AmbientOcclusion.HBAO]=[=[
		PostProcess.DynamicAOEnable 1
		PostProcess.DynamicAOMethod DynamicAOMethod_HBAO
		PostProcess.HbaoHalfResEnable 1
	]=],
	[AmbientOcclusion.HBAOFull]=[=[
		PostProcess.DynamicAOEnable 1
		PostProcess.DynamicAOMethod DynamicAOMethod_HBAO
		PostProcess.HbaoHalfResEnable 0
	]=],
})

------- Motion Blur
WorldRender = WorldRender or {}
-- for some reason motion we get "attempt to perform arithmetic on field 'MotionBlur' (a nil value)" when starting up so workaround by having a script default value to handle that case --johan
WorldRender.MotionBlurScale = (settings['MotionBlur'] or 1.0)


------- Weapon DOF
PostProcess.IronsightsDofEnable = settings['WeaponDOF']

------- Screen resolution
RenderDevice = RenderDevice or {}
RenderDevice.FullscreenWidth = settings['ResolutionWidth']
RenderDevice.FullscreenHeight = settings['ResolutionHeight']
RenderDevice.FullscreenRefreshRate = settings['FullscreenRefreshRate']
RenderDevice.Fullscreen = settings['FullscreenEnabled']
RenderDevice.FullscreenOutputIndex = settings['FullscreenScreen']
RenderDevice.VSyncEnable = settings['VSyncEnabled']
RenderDevice.StereoEnable = settings['Stereoscopy']
RenderDevice.StereoDepth = settings['StereoConvergence']

-------- Resolution scale
Render.ResolutionScale = settings['ResolutionScale']

-------- Brightness
PostProcess.UIBrightnessNorm = settings['Brightness']