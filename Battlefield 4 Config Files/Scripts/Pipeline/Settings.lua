-- Internal Pipeline settings
--
--   This is not really the best place to change settings locally,
--   please see GlobalSettings.lua instead and use it if at all
--   possible
--

local Dx10PathEnable = false
local Dx10PlusPathEnable = true
local Dx10Dot1PathEnable = false
local Dx11PathEnable = true
local AutoPathEnable = false
local SkipAllPaths = false

Pipeline = 
{
	Verbose = false,
	ShowReason = true,
	AddToPerforce = false,

	-- Debugging/profiling options
	DebugMode = false,
	EmitPlaintextDbx = false,
	EnableTimingLog = false,
	BundleDebugging = false,
	ResourceDebugging = false,

	AsyncOpWrite = true,
	DumpOplogStats = false,

	--ServiceMode = true,
}

--------------------------------------------------------------

Core = 
{
	LogLevel = "Info",
	StudioBus = "4803@10.20.96.148"  -- dice-bm150
}

Ant = 
{
--	Setting this to true sometimes breaks reimport
	ShowSyncDialogOnReimport = false,
--	Display a warning if 'allow' is true or an error if 'allow' false if an animset is built that requires the traditional runtime. Only relevant when FB_ANT_ROOTCONTROLLER_ENABLE is set to true in diceconfig.
	AllowTraditionalAssetsWithRootcontrollerEnabled = false,
}

Havok = 
{
	WriteShapeLog = false,
	WriteMemoryLog = false,
	WriteMaterialsLog = false,
	ErrorOnStaticValidationError = true,	
	DetailExport = true,
	LogTriangleDensity = true,
	HavokImportVersion = 1,
}

HavokGroupPhysicsEntity =
{
	WriteShapeLog = false,
	WriteMemoryLog = false,
	WriteMaterialsLog = false,
	WriteGroupGeneralLog = false,
	WritePartInfoLog = false,
	ErrorOnStaticValidationError = true,	
	DetailExport = true,
	LogTriangleDensity = true,
	HavokImportVersion = 1,

	AllowDefaultCollisionShape = true
}

Texture =
{
	SkipMipmapsPs3Enable = true,
	CudaEnable = true,
	WaitOnCudaEnable = false,
	MultithreadedCompressionEnable = true,
	DefaultTextureGroup = "TextureGroups/Default";
}

ObjectBlueprint =
{
	AllowDefaultPartMaterial = true,
	AllowDefaultEntityMaterial = true,
}

BlueprintImport =
{
	UseDefaultRaycastRoot = false,
	OptimizeGeometries = true,
	ErrorOnSceneMismatch = true,
}

Mesh = 
{
	Verbose = false,
	ZOnlyMeshEnable = true,
	Ps3EdgeEnable = true,
	Ps3EdgeCompressPositionsEnable = true,	
	Ps3EdgeCompressIndicesEnable = true,	
	Ps3EdgeMinSubsetTriangleCount = 0,
	
	MinStreamingLodSize = 0,

	Ps3EdgeKeepIndexBufferEnable = false,
	
	SootSkipShaderNames =
	{
		"Template",
		"Tmpl",
		"NoSoot"
	},
	WarningsAsErrorsEnable = false,
	
	-- EnlightenSaveInputMeshEnable = true,
	-- RadiosityPixelSize_Lower = 16.0,
	-- RadiosityPixelSize_Lowest = 8.0,
	-- RadiosityPixelSize_Low = 4.0,
	-- RadiosityPixelSize_Medium = 2.0,
	-- RadiosityPixelSize_High = 1.0,
	-- RadiosityPixelSize_Higher = 0.5,
	-- RadiosityPixelSize_Max = 0.25,
	
	-- LightmapDxtBlockSizeEnable = true,
}

Enlighten = 
{
	-- Set this to false to prevent any dynamic enlighten data from beeing built and loaded into the game.
	-- This is useful for retail builds if only static enlighten data is allowed.
	DynamicEnable = true,
}

Level = 
{
	VoAnimTreesEnable = false,

	SpatialSubdivision = true,
	EnableGroupLogs = false,
	GroupStaticModelEntities = true,
	GroupTreeModelEntities = true,
	GroupMiddlegrowthModelEntities = true,
	
	LogDetailedInstanceCounts = false	
}

SubLevel = 
{
	SpatialSubdivision = true,
	EnableGroupLogs = false,
	OptimizeMaterialGrid = true,
	GroupStaticModelEntities = true,
}

BlueprintBundleCollection =
{
	InclusionWildcards =
	{
--		{ Collection="MyBundleCollection1", Wildcard="MyWildcard1" },
--		{ Collection="MyBundleCollection1", Wildcard="MyWildcard2" },
--		{ Collection="MyBundleCollection2", Wildcard="MyWildcard3" }
	}
}

ShaderState =
{
	TerrainDebugColorEnable = false,
	TerrainOverdrawModeEnable = false,
	DynamicEnvmapModeEnable = true,
	DynamicEnvmapModeModernOnly = true,
	
	StripUnusedTextures = true,

	VanillaShadowmapsEnable = false,

	Win32QualityLevel = -1,
	XenonQualityLevel = 0,
	Ps3QualityLevel = 0,
	Gen4aQualityLevel = 2,
	Gen4bQualityLevel = 2		

--	CascadedBox3ShadowmapsEnable = true, 
--	CascadedBox4ShadowmapsEnable = true, 	
}

ShaderProgramDatabase =
{
	ShaderDebugInfoEnable = true,
	DxShaderDebugInfoEnable = false,

	Dx10PathEnable = Dx10PathEnable,	
	Dx10PlusPathEnable = Dx10PlusPathEnable,
	Dx10Dot1PathEnable = Dx10Dot1PathEnable,
	Dx11PathEnable = Dx11PathEnable,
	AutoPathEnable = AutoPathEnable,
	SkipAllPaths = SkipAllPaths,

	CacheEnable = true,
	
	Ps3OptimizationLevel = 3,
	
	DynamicEnvmapModeEnable = true,
	DynamicEnvmapModeModernOnly = true,	
	
	Ps3BuildRandomizedShaders = false
}

ShaderDatabase = 
{
	ShaderDebugInfoEnable = false,
	DxShaderDebugInfoEnable = false,

	Dx10PathEnable = Dx10PathEnable,	
	Dx10PlusPathEnable = Dx10PlusPathEnable,
	Dx10Dot1PathEnable = Dx10Dot1PathEnable,
	Dx11PathEnable = Dx11PathEnable,
	AutoPathEnable = AutoPathEnable,
	SkipAllPaths = SkipAllPaths,

	Ps3OptimizationLevel = 3,

	-- enabled by default in Warsaw when shipping, disable this locally if you want faster PS3 build times when changing shaders
	Ps3BuildRandomizedShaders = false,

	Ps3PackVertexElementsEnable = true,
	Ps3ForceHalfPrecisionEnable = false,
	Ps3ForceVertexDynamicBranchesEnable = false,
	Ps3ForcePixelDynamicBranchesEnable = false,
	
	ForceCompileInConstantsEnable = true,
	NonFiniteColoringEnable = false,
	ManualSourceChangeEnable = false,
	StateMetricsEnable = true,
	StateSolutionCacheEnable = false,
	SeparateWorldTransformEnable = false
}

Terrain = 
{
	SplineDecalsSubdivisionCount = 1,
	MaskFunctionsEnable = false,
}

TerrainStreamingTree =
{
	OptimizeUsingProceduralMask = true
}

Award =
{
	OutputAwardXml = true,
	OutputRankXml = true,
	InvalidAwardCodeCharacters = "-. ",
	AllowEmptyAwardCodes = false,
	CheckStatCategories = true,
	SetSpecificDataToChildAwards = true,
}

Persistence =
{
	OutputXml = true,
	OutputBlazeProperties = true,
	DeltaGameReports = true,
}

Unlock =
{
	ExportUnlocks = true
}

AtlasTexture =
{
	SkipMipmaps = 0,
	SkipMipmapsPs3 = 0,
	SkipMipmapsXenon = 0,	
	SkipMipmapsGen4a = 0,
	SkipMipmapsGen4b = 0,

	MinMipmapCount = 6,
	MinMipmapCountPs3 = 4,
	MinMipmapCountXenon = 4,
	MinMipmapCountGen4a = 6,
	MinMipmapCountGen4b = 6,

	MinNormalMipmapCount = 4,
	MinNormalMipmapCountPs3 = 1,
	MinNormalMipmapCountXenon = 1,
	MinNormalMipmapCountGen4a = 3,
	MinNormalMipmapCountGen4b = 3,
}

Audio =
{
	EnableDevelopmentFeatures = false,
	EnableLocalizedSandboxes = false,
}

UIActionscriptInjection =
{
	RemoveTraces = true,
	DefinesToKeep =
	{
		--"LOG",
		--"DEBUG",
	}
}

UI =
{
	TextureGroupPath = "TextureGroups/UI",
}

VeniceLevel =
{
	GenerateCombatAreaTextures = false,
}

GameConfig =
{
	AnimationProjectPath = "Animations/AntAnimations",
}

SoldierWeaponBlueprint = 
{
	DisableOnDemandLoading = false,
}
