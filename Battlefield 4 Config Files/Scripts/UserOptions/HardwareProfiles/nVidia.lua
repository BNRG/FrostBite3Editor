-- Settings database for nVidia devices
return {
--	[0xHHHH] = { -- Example card
--		baseQualityLevel = Quality.Medium,
--		defaultSettings = {
--			TextureQuality = Quality.Low,
--			ShadowQuality = Quality.Medium,
--			EffectsQuality = Quality.Medium,
--		},
--		consoleOverrides = [=[
--			WorldRender.MotionBlurEnable 0
--		]=]
--	}
	[0x0191] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 GTX
	[0x0193] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 GTS
	[0x0194] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 Ultra
	[0x019D] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 5600
	[0x019E] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 4600
	[0x0400] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8600 GTS
	[0x0401] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8600 GT
	[0x0402] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8600 GT 
	[0x0403] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8600 GS
	[0x0404] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400 GS
	[0x0405] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9500M GS
	[0x0406] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8300 GS
	[0x0407] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8600M GT
	[0x0408] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9650M GS
	[0x0409] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8700M GT
	[0x040A] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 370
	[0x040B] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 320M
	[0x040C] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 570M
	[0x040D] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 1600M
	[0x040E] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 570
	[0x040F] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 1700
	[0x0410] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 330
	[0x0420] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400 SE
	[0x0421] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8500 GT
	[0x0422] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400 GS 
	[0x0423] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8300 GS 
	[0x0424] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400 GS  
	[0x0425] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8600M GS
	[0x0426] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400M GT
	[0x0427] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400M GS
	[0x0428] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400M G
	[0x0429] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 140M
	[0x042A] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 130M
	[0x042B] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 135M
	[0x042C] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400 GT
	[0x042D] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 360M
	[0x042E] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300M G
	[0x042F] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 290
	[0x05E0] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 295
	[0x05E1] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 280
	[0x05E2] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 260
	[0x05E3] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 285
	[0x05E6] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 275
	[0x05EA] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 260 
	[0x05EB] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 295 
	[0x05F9] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro CX
	[0x05FD] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro FX 5800
	[0x05FE] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro FX 4800
	[0x05FF] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 3800
	[0x0600] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 GTS 512
	[0x0601] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800 GT
	[0x0602] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 GT
	[0x0603] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 230
	[0x0604] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800 GX2
	[0x0605] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800 GT 
	[0x0606] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 GS
	[0x0607] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTS 240
	[0x0608] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800M GTX
	[0x0609] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800M GTS
	[0x060A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTX 280M
	[0x060B] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800M GT
	[0x060C] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800M GTX
	[0x060F] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTX 285M
	[0x0610] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GSO
	[0x0611] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8800 GT 
	[0x0612] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800 GTX/9800 GTX+
	[0x0613] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800 GTX+
	[0x0614] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800 GT  
	[0x0615] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTS 250
	[0x0617] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800M GTX
	[0x0618] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTX 260M
	[0x0619] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 4700 X2
	[0x061A] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 3700
	[0x061B] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro VX 200
	[0x061C] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 3600M
	[0x061D] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 2800M
	[0x061E] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 3700M
	[0x061F] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 3800M
	[0x0621] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 230 
	[0x0622] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GT
	[0x0623] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GS
	[0x0625] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GSO 512
	[0x0626] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 130
	[0x0627] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 140
	[0x0628] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800M GTS
	[0x062A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9700M GTS
	[0x062B] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800M GS
	[0x062C] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9800M GTS  
	[0x062D] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GT 
	[0x062E] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GT  
	[0x0631] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTS 160M
	[0x0635] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GSO 
	[0x0637] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600 GT   
	[0x0638] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 1800
	[0x063A] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 2700M
	[0x0640] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9500 GT
	[0x0641] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400 GT 
	[0x0643] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9500 GT 
	[0x0644] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9500 GS
	[0x0645] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9500 GS 
	[0x0646] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 120
	[0x0647] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600M GT
	[0x0648] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600M GS
	[0x0649] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9600M GT
	[0x064A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9700M GT
	[0x064B] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9500M G
	[0x064C] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9650M GT
	[0x0651] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G 110M
	[0x0652] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 130M
	[0x0653] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 120M
	[0x0654] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 320M
	[0x0655] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 120 
	[0x0656] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 120  
	[0x0658] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 380
	[0x0659] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 580
	[0x065A] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 1700M
	[0x065B] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400 GT  
	[0x065C] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 770M
	[0x065F] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G210
	[0x06C0] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 480
	[0x06C4] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 465
	[0x06CA] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 480M
	[0x06CD] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 470
	[0x06D8] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 6000
	[0x06D9] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 5000
	[0x06DA] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 5000M
	[0x06DC] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 6000  
	[0x06DD] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 4000
	[0x06E0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300 GE
	[0x06E1] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300 GS
	[0x06E2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400
	[0x06E3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400 SE 
	[0x06E4] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400 GS    
	[0x06E6] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G100
	[0x06E7] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300 SE
	[0x06E8] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9200M GS
	[0x06E9] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300M GS
	[0x06EA] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 150M
	[0x06EB] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 160M
	[0x06EC] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G 105M
	[0x06EF] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G 103M
	[0x06F1] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G105M
	[0x06F8] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 420
	[0x06F9] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 370 LP
	[0x06FA] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 450
	[0x06FB] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 370M
	[0x06FD] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro NVS 295
	[0x06FF] = { baseQualityLevel = Quality.Low }, -- NVIDIA HICx16 + Graphics
	[0x0840] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8200M
	[0x0844] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9100M G
	[0x0845] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8200M G
	[0x0846] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9200 
	[0x0847] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9100
	[0x0848] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8300
	[0x0849] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8200
	[0x084A] = { baseQualityLevel = Quality.Low }, -- NVIDIA nForce 730a
	[0x084B] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9200  
	[0x084C] = { baseQualityLevel = Quality.Low }, -- NVIDIA nForce 980a/780a SLI
	[0x084D] = { baseQualityLevel = Quality.Low }, -- NVIDIA nForce 750a SLI
	[0x084F] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8100 / nForce 720a
	[0x0860] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400
	[0x0861] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400 
	[0x0862] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400M G
	[0x0863] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400M
	[0x0864] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300
	[0x0865] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION    
	[0x0866] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400M G
	[0x0867] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400 
	[0x0868] = { baseQualityLevel = Quality.Low }, -- NVIDIA nForce 760i SLI
	[0x0869] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400  
	[0x086A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400   
	[0x086C] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300 / nForce 730i
	[0x086D] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9200   
	[0x086E] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9100M G
	[0x086F] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8200M G
	[0x0870] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400M        
	[0x0871] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9200    
	[0x0872] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G102M
	[0x0873] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G102M   
	[0x0874] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION     
	[0x0876] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION
	[0x087A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9400    
	[0x087D] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION      
	[0x087E] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION LE
	[0x087F] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION LE 
	[0x08A0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 320M
	[0x08A2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 320M  
	[0x08A3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 320M   
	[0x08A4] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 320M    
	[0x0A20] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 220 
	[0x0A22] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 315
	[0x0A23] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 210
	[0x0A26] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 405
	[0x0A27] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 405 
	[0x0A28] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 230M
	[0x0A29] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 330M
	[0x0A2A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 230M
	[0x0A2B] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 330M 
	[0x0A2C] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 5100M
	[0x0A2D] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 320M
	[0x0A32] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 415
	[0x0A34] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 240M
	[0x0A35] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 325M
	[0x0A38] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro 400
	[0x0A3C] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 880M
	[0x0A60] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G210 
	[0x0A62] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 205
	[0x0A63] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 310
	[0x0A64] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION 
	[0x0A65] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 210 
	[0x0A66] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 310 
	[0x0A67] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 315 
	[0x0A68] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G105M
	[0x0A69] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G105M
	[0x0A6A] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 2100M
	[0x0A6C] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 3100M
	[0x0A6E] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 305M
	[0x0A6F] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION  
	[0x0A70] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 310M
	[0x0A71] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 305M
	[0x0A72] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 310M           
	[0x0A73] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 305M 
	[0x0A74] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce G210M
	[0x0A75] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 310M               
	[0x0A76] = { baseQualityLevel = Quality.Low }, -- NVIDIA ION    
	[0x0A78] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 380 LP
	[0x0A7A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 405
	[0x0A7C] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 380M
	[0x0CA0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 330 
	[0x0CA2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 320
	[0x0CA3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 240
	[0x0CA4] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 340
	[0x0CA5] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 220  
	[0x0CA7] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 330  
	[0x0CA9] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTS 250M
	[0x0CAC] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 220   
	[0x0CAF] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 335M
	[0x0CB0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTS 350M
	[0x0CB1] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTS 360M
	[0x0CBC] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro FX 1800M
	[0x0DC0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 440
	[0x0DC4] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTS 450
	[0x0DC5] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTS 450 
	[0x0DC6] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTS 450  
	[0x0DCD] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 555M
	[0x0DCE] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 555M
	[0x0DD1] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 460M
	[0x0DD2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 445M
	[0x0DD3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 435M 
	[0x0DD6] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 550M  
	[0x0DD8] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 2000
	[0x0DDA] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro 2000M 
	[0x0DE0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 440 
	[0x0DE1] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 430
	[0x0DE2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 420
	[0x0DE3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 635M 
	[0x0DE4] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 520
	[0x0DE5] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 530
	[0x0DE8] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 620M
	[0x0DE9] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 630M
	[0x0DEA] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 610M
	[0x105B] = { baseQualityLevel = Quality.Low }, -- GeForce 705M
	[0x1295] = { baseQualityLevel = Quality.Low }, -- GeForce 710M
	[0x1298] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 720M
	[0x0DEB] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 555M
	[0x0DEC] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 525M
	[0x0DED] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 520M
	[0x0DEE] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 415M
	[0x0DEF] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 5400M 
	[0x0DF0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 425M
	[0x0DF1] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 420M
	[0x0DF2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 435M
	[0x0DF3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 420M 
	[0x0DF4] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 540M
	[0x0DF5] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 525M
	[0x0DF6] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 550M
	[0x0DF7] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 520M
	[0x0DF8] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 600
	[0x0DF9] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 500M
	[0x0DFA] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro 1000M
	[0x0DFC] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 5200M
	[0x1290] = { baseQualityLevel = Quality.Low }, -- GeForce GT 730M (GDDR5)
	[0x0FE1] = { baseQualityLevel = Quality.Low }, -- GeForce GT 730M (DDR3)
	[0x1291] = { baseQualityLevel = Quality.Low }, -- GeForce GT 735M
	[0x0E22] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 460
	[0x0E23] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 460 SE
	[0x0E24] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 460 
	[0x0E30] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 470M
	[0x0E31] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 485M
	[0x0E3A] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro 3000M
	[0x0E3B] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro 4000M
	[0x0F00] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 630
	[0x0F01] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 620
	[0x0FC0] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GT 640
	[0x0FC1] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GT 640 
	[0x0FC2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 630 
	[0x0FC6] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 650
	[0x0FD1] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 650M
	[0x0FD2] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 640M
	[0x0FD3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 640M LE
	[0x0FD4] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 660M
	[0x0FD5] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 650M
	[0x0FD8] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 640M
	[0x0FD9] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 645M
	[0x0FE0] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 660M
	[0x0FDF] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 740M (DDR3)
	[0x1292] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 740M
	[0x1293] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 740M
	[0x1294] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 740M (GDDR5)
	[0x0FF9] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro K2000D
	[0x0FFA] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro K600
	[0x0FFB] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro K2000M 
	[0x0FFC] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro K1000M 
	[0x0FFD] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 510
	[0x0FFE] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro K2000
	[0x0FFF] = { baseQualityLevel = Quality.Low }, -- NVIDIA Quadro 410
	[0x1040] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 520 
	[0x1042] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 510
	[0x1048] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 605
	[0x1049] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 620 
	[0x104A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 610
	[0x1050] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 520M
	[0x1051] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 520MX
	[0x1052] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 520M      
	[0x1054] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 410M
	[0x1055] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 410M
	[0x1056] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 4200M
	[0x1057] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 4200M 
	[0x1058] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 610M
	[0x1059] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 610M 
	[0x105A] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 610M       
	[0x107D] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 310
	[0x1080] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 580
	[0x1081] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 570
	[0x1082] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560 Ti
	[0x1084] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560
	[0x1086] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 570 
	[0x1087] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560 Ti 
	[0x1088] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 590
	[0x1089] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 580 
	[0x108B] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 580  
	[0x109A] = { baseQualityLevel = Quality.Medium }, -- NVIDIA Quadro 5010M 
	[0x109B] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro 7000
	[0x10C0] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 9300 GS 
	[0x10C3] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 8400GS
	[0x10C5] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce 405  
	[0x10D8] = { baseQualityLevel = Quality.Low }, -- NVIDIA NVS 300
	[0x1140] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 620M        
	[0x1180] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 680
	[0x1183] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 660 Ti
	[0x1185] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 660
	[0x1188] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 690
	[0x1189] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 670
	[0x11A0] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 680M
	[0x11A1] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 670MX
	[0x11A2] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 675MX
	[0x11A3] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 680MX
	[0x11A7] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 675MX
	[0x11BA] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro K5000
	[0x11BC] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro K5000M
	[0x11BD] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro K4000M
	[0x11BE] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro K3000M
	[0x11BF] = { baseQualityLevel = Quality.High }, -- NVIDIA GRID K2
	[0x11C0] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 660 
	[0x11C6] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 650 Ti 
	[0x11FA] = { baseQualityLevel = Quality.High }, -- NVIDIA Quadro K4000
	[0x1200] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560 Ti  
	[0x1201] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560 
	[0x1205] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 460 v2
	[0x1206] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce  GTX 555
	[0x1207] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GT 645
	[0x1208] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560 SE
	[0x1210] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 570M
	[0x1211] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 580M
	[0x1212] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 675M
	[0x1213] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 670M
	[0x1241] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GT 545
	[0x1243] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 545 
	[0x1244] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTX 550 Ti
	[0x1245] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GTS 450   
	[0x1246] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 550M
	[0x1247] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 635M
	[0x1248] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GT 555M
	[0x1249] = { baseQualityLevel = Quality.Low }, -- NVIDIA GeForce GTS 450    
	[0x124B] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GT 640  
	[0x124D] = { baseQualityLevel = Quality.Medium }, -- NVIDIA GeForce GT 555M 
	[0x1251] = { baseQualityLevel = Quality.High }, -- NVIDIA GeForce GTX 560M
	[0x0FE2] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 745M (DDR3)
	[0x0FE3] = { baseQualityLevel = Quality.Medium }, -- GeForce GT 745M (GDDR5)
	[0x0FE9] = { baseQualityLevel = Quality.High }, -- GeForce GT 750M (DDR3)
	[0x0FE4] = { baseQualityLevel = Quality.High }, -- GeForce GT 750M (GDDR5)
	[0x0FEA] = { baseQualityLevel = Quality.High }, -- GeForce GT 755M
	[0x0FCD] = { baseQualityLevel = Quality.High }, -- GeForce GT 755M
	[0x11E3] = { baseQualityLevel = Quality.High }, -- GeForce GTX 760M
	[0x11E1] = { baseQualityLevel = Quality.High }, -- GeForce GTX 765M
	[0x11E2] = { baseQualityLevel = Quality.High }, -- GeForce GTX 765M
	[0x11E0] = { baseQualityLevel = Quality.High }, -- GeForce GTX 770M
	[0x119E] = { baseQualityLevel = Quality.High }, -- GeForce GTX 780M
	[0x119F] = { baseQualityLevel = Quality.High }, -- GeForce GTX 780M
	[0x1187] = { baseQualityLevel = Quality.High }, -- GeForce GTX 760
	[0x119F] = { baseQualityLevel = Quality.High }, -- GeForce GTX 780M
	[0x1184] = { baseQualityLevel = Quality.High }, -- GeForce GTX 770
	[0x1004] = { baseQualityLevel = Quality.High }, -- GeForce GTX 780
	[0x1005] = { baseQualityLevel = Quality.Ultra }, -- GeForce GTX TITAN
}