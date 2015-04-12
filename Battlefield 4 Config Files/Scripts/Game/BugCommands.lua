{	
	-- Bug needed for when using Trigger bug in B4Bug, info is used for all bugs
	StandardB4BugBug =
	{
		Name = "Standard B4Bug bug",
		Title = "Venice",
		Menu = "Bugs",
		AutoB4Bug = true,
		IsStandardBug = true,
		
		Information =
		{
			"Field" , "Issue Type" , "Bug",
			"Field" , "Priority" , "Minor (C, V3, L3)",
			"Field" , "Assignee" , "Automatic",
			"Field" , "Report Method" , "B4Bug",
			"Field" , "Milestone Hindering" , "None",
			"Field" , "Language" , "English",
			"Field" , "User Path" , "Probable",
			"RPCGet", "Game Level" , "LocalPlayer.getClientLevelName",
			
			"Field" , "Description" , "\nSessionOId: ",
			"RPCGet", "Description" , "BugManager.getSessionOId",
			"Field" , "Description" , "\nFrostbite Release: ",
			"RPCGet", "Description" , "BugManager.getFrostbiteRelease",
			"Field" , "Description" , "\nSoldier position: ",
			"RPCGet", "Description" , "LocalPlayer.getPlayerPosition",
			"Field" , "Description" , "\nCurrent camera position: ",
			"RPCGet", "Description" , "DebugCam.getCurrentCameraPosition",
			
			"Field" , "Environment" , "\nOS Version: ",
			"RPCGet", "Environment" , "BugManager.getOSInfo",
			"Field" , "Environment" , "\nGPU description: ",
			"RPCGet", "Environment" , "DxRenderer.AdapterDescription",
			"Field" , "Environment" , "\nGPU driver version: ",
			"RPCGet", "Environment" , "DxRenderer.AdapterDriverVersion",
			
			"RPCGet", "Platform" , "BugManager.getPlatform",
			"RPCGet", "Reported By (Game Team)" , "BugManager.getReportedBy",
			"RPCGet", "Found in Change List (Game)" , "BugManager.getChangelist",
			"RPCGet", "Found in Change List (Frostbite)" , "BugManager.getFrostbiteChangelist",
			"RPCGet" , "Juice Session ID" , "BugManager.getjuiceSessionId",
		},
		
		RPCs =
		{
			"BugManager.forwardTrace",
		},
	},
		
	TestBug1 =
	{
		Name = "Testbug",
		Title = "Venice",
		Menu = "Bugs",
		AutoB4Bug = true,
		
		Information =
		{
		},
		
		RPCs =
		{
		},
	},
	
	TestBug2 =
	{
		Name = "Biometricsbug",
		Title = "Warsaw",
		Menu = "Bugs",
		AutoB4Bug = false,
		
		Information =
		{
		},
		
		RPCs =
		{
			"BugManager.RPCHandler"
		},
	},
	
	TestCommand1 = 
	{
		Name = "Testcommand 1",
		Title = "Venice",
		Menu = "QA Commands",
		AutoB4Bug = false,
		
		RPCs =
		{
			"DebugRender.DrawStatsEnable 1",
			"UI.DrawEnable 0",
		},
		
		ReverseRPCs =
		{
			"DebugRender.DrawStatsEnable 0",
			"UI.DrawEnable 1",
		},
	},

	TestCommand2 = 
	{
		Name = "Testcommand 2",
		Title = "Venice",
		Menu = "QA Commands",
		AutoB4Bug = false,
		
		RPCs =
		{
			"Debug.DrawScreenCenterHelper 1",
			"Render.ForceFov 100",
		},
		
		ReverseRPCs =
		{
			"Debug.DrawScreenCenterHelper 0",
			"Render.ForceFov 65",
		},
	},
	
	AnimationCommand1 = 
	{
		Name = "Animation Test",
		Title = "Venice",
		Menu = "Animation",
		AutoB4Bug = false,
		
		RPCs =
		{
			"AntDebug.DrawDebugInfo 1",
			"AntDebug.DrawSignals 1",
			"UI.DrawEnable 0",
		},
		
		ReverseRPCs =
		{
			"AntDebug.DrawDebugInfo 0",
			"AntDebug.DrawSignals 0",
			"UI.DrawEnable 1",
		},
	},

	WeaponCommand1 = 
	{
		Name = "Show weapon info",
		Title = "Venice",
		Menu = "Weapons",
		AutoB4Bug = false,
		
		RPCs =
		{
			"AntDebug.DrawDebugInfo 1",
			"Debug.DrawScreenCenterHelper 1",
			"clientsoldier.debug.firingstats 1",
		},
		
		ReverseRPCs =
		{
			"AntDebug.DrawDebugInfo 0",
			"Debug.DrawScreenCenterHelper 0",
			"clientsoldier.debug.firingStats 0",
		},
	},	

	WeaponCommand2 = 
	{
		Name = "Set next weapon",
		Title = "Venice",
		Menu = "Weapons",
		AutoB4Bug = false,
		
		RPCs =
		{
		    "LocalPlayer.setNextAvailableWeaponFromSet",
		},
		
		ReverseRPCs =
		{
		},
	},	
}