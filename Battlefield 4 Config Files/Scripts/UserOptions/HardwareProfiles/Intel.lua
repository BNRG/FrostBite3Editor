-- Settings database for Intel devices
return {
--	[0xHHHH] = { -- Example card
--		baseQualityLevel = Quality.High,
--		defaultSettings = {
--			TextureQuality = Quality.Low,
--			ShadowQuality = Quality.Medium,
--			EffectsQuality = Quality.High,
--		},
--		consoleOverrides = [=[
--			WorldRender.MotionBlurEnable 0
--		]=]
--	}

	[0x2982] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) G35 Express Chipset Family
	[0x2983] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) G35 Express Chipset Family
	[0x2A02] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Mobile Intel(R) 965 Express Chipset Family
	[0x2A03] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Mobile Intel(R) 965 Express Chipset Family
	[0x2A12] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Mobile Intel(R) 965 Express Chipset Family
	[0x2A13] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Mobile Intel(R) 965 Express Chipset Family
	[0x2A42] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Mobile Intel(R) 4 Series Express Chipset Family
	[0x2A43] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Mobile Intel(R) 4 Series Express Chipset Family
	[0x2E02] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) 4 Series Express Chipset
	[0x2E03] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) 4 Series Express Chipset
	[0x2E22] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) G45/G43 Express Chipset
	[0x2E23] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) G45/G43 Express Chipset
	[0x2E12] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) Q45/Q43 Express Chipset
	[0x2E13] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) Q45/Q43 Express Chipset
	[0x2E32] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) G41 Express Chipset
	[0x2E33] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) G41 Express Chipset
	[0x2E42] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) B43 Express Chipset
	[0x2E43] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) B43 Express Chipset
	[0x2E92] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) B43 Express Chipset
	[0x2E93] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) B43 Express Chipset
	[0x0046] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) HD Graphics - Core i3/i5/i7 Mobile Processors
	[0x0042] = { baseQualityLevel = Quality.Low    }, -- SM4   ; Intel(R) HD Graphics - Core i3/i5 + Pentium G9650 Processors
	[0x0106] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 2000 - Mobile SandyBridge GT1
	[0x0102] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 2000 - Desktop SandyBridge GT1
	[0x010A] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 3000 - Server SandyBridge 
	[0x0152] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 2500 - Desktop IvyBridge GT1
	[0x0156] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 2500 - Mobile IvyBridge GT1
	[0x015A] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 2500 - Server IvyBridge GT1
	[0x015E] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 2500 - Reserved - IvyBridge GT1
	[0x0A06] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Mobile Haswell - ULT GT1
	[0x0A0E] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Reserved Haswell - ULT GT1
	[0x0402] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Desktop Haswell GT1
	[0x0406] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Mobile Haswell GT1
	[0x040A] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Server Haswell GT1
	[0x0112] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 3000 - Desktop SandyBridge GT2
	[0x0122] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 3000 - Desktop SandyBridge GT2+
	[0x0116] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 3000 - Mobile SandyBridge GT2
	[0x0126] = { baseQualityLevel = Quality.Low    }, -- SM4.1 ; Intel(R) HD Graphics 3000 - Mobile SandyBrdige GT2+
	[0x0162] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 4000 - Desktop IvyBridge GT2
	[0x0166] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 4000 - Mobile IvyBridge GT2
	[0x016A] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; Intel(R) HD Graphics 4000 - Server IvyBrdige GT2
	[0x0D12] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Mobile Haswell - ULT GT2
	[0x0D16] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 4600 - Mobile Haswell - ULT GT2
	[0x0416] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Mobile Haswell GT2
	[0x041B] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Workstation Haswell GT2
	[0x041A] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics - Server Haswell GT2
	[0x0A1E] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 4200 - Mobile Haswell - ULT GT2
	[0x0A16] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 4400 - Mobile Haswell - ULT GT2
	[0x041E] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 4400 - Reserved Haswell 
	[0x0412] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 4600 - Desktop Haswell GT2
	[0x0A26] = { baseQualityLevel = Quality.Low    }, -- SM5.0 ; 4th Gen Intel(r) Core processor graphics HD 5000 - Mobile Haswell - ULT GT3
	[0x0A2E] = { baseQualityLevel = Quality.Medium }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 5100 Iris - Mobile Haswell - ULT GT3
	[0x0D26] = { baseQualityLevel = Quality.Medium }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 5200 Iris Pro -  Mobile GT3
	[0x0D22] = { baseQualityLevel = Quality.Medium }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics HD 5200 Iris Pro - Desktop GT3
	[0x0D2A] = { baseQualityLevel = Quality.Medium }, -- SM5.0 ; 4th Gen Intel(R) Core processor graphics Iris Pro - Server GT3	
}