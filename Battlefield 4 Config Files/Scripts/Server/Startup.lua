function Startup()

  print("Startup called")

  local currentPlatform = string.lower(dice.getCurrentPlatformName())
  if (currentPlatform == "xenon" or currentPlatform == "ps3") then
    return
  end
  
  Frost.Core.import(serverSaveFilePath, "Server Admin file", {silent=true})
  print("Loaded banList")
  for k,v in pairs(banList) do
	currentTime = os.date("%c", v.time)
    print("[" .. k .. "]: " .. v.name .. "," .. currentTime)
  end
  
  print("Loaded levelList")
  for k,v in pairs(levelList) do
	maxLevelIndex = maxLevelIndex + 1
    print("[" .. k .. "]: " .. v)
  end
  
  if (maxLevelIndex > 0) then
    currentLevelIndex = 1
  end

end