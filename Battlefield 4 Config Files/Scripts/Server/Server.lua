require "vfs"
require "os"

banList = {}
levelList = {}
currentLevelIndex = 0
maxLevelIndex = 0

function Server_LoadLevel()

--  print("Server_LoadLevel called")

end

function Server_UnloadLevel()

--  print("Server_UnloadLevel called")

end


function Server_LevelLoaded()

--  print("Server_LevelLoaded called")

end

function Server_LevelUnloaded()

--  print("Server_LevelUnloaded called")

end


ticks = 0

function Server_ScriptTick()

--  print("Server_ScriptTick called; ticks = " .. ticks)

  ticks = ticks + 1

end

function Server_PlayerBanned(onlineId, name)

  print("Server_PlayerBanned called: Player " .. onlineId .. " banned")

  banList[onlineId] = { ["time"]=os.time(), ["name"]=name }
  
  writeServerData()

end

function Server_UnbanPlayer(id)

  if (banList[id] ~= nil) then
    print("Player " .. id .. " unbanned")
    banList[id] = nil
    
    if (banList[id] ~= nil) then
	  print("Player was not removed for some reason")
    end
    
    writeServerData()
  else
    print("No player with id " .. id .. " is banned")
  end

end

function Server_ListBans()
 
  --Server_PlayerBanned(174636, "gunnar")
  --Server_PlayerBanned(345636, "olle")
  
  for k,v in pairs(banList) do
    currentTime = os.date("%c", v.time)
    print("[" .. k .. "]: " .. v.name .. "," .. currentTime)
  end
  
end

function Server_LoadNextLevel()

  if (levelList[currentLevelIndex] ~= nil) then
	print("Loading level: " .. levelList[currentLevelIndex])
	loadNextLevel(levelList[currentLevelIndex])
	if (currentLevelIndex < maxLevelIndex) then
	  currentLevelIndex = currentLevelIndex + 1
	else
	  currentLevelIndex = 1
	end
  else
    print("Levelindex " .. currentLevelIndex .. " do not exist")
  end
  
end

function Server_AddLevel(levelName)

  maxLevelIndex = maxLevelIndex + 1	
  levelList[maxLevelIndex] = levelName
  print("Added level '" .. levelName .. "' to level list")
  writeServerData()
  if (currentLevelIndex == 0) then
    currentLevelIndex = 1
  end
  
end

function Server_RemoveLevel(levelIndex)

  if (levelList[levelIndex] ~= nil) then
	print("Removing level '" .. levelList[levelIndex] .. "' at index: " .. levelIndex)
	--table.remove(levelList, levelIndex)
	for i=levelIndex,(maxLevelIndex-1),1 do
	  levelList[i] = levelList[i+1]
	end
	table.remove(levelList, maxLevelIndex)
	maxLevelIndex = maxLevelIndex - 1
	
	writeServerData()
	if (currentLevelIndex > maxLevelIndex) then
      currentLevelIndex = maxLevelIndex
    end
  else
    print("No level at index: " .. levelIndex)
  end

end

function Server_ListLevels()
   
  for k,v in pairs(levelList) do
	if (k == currentLevelIndex) then
	  print("[" .. k .. "]: " .. v .. " <<")
	else
	  print("[" .. k .. "]: " .. v)
	end
  end
  
end

function writeServerData()

  local currentPlatform = string.lower(dice.getCurrentPlatformName())
  if (currentPlatform == "xenon" or currentPlatform == "ps3") then
    return
  end
  
  local f = vfs.open(serverSaveFilePath, "w")
  
  if not f then
	-- File not found
	return
  end
  
  serializedTable = tableToString(banList)
  
  f:write("banList = " .. serializedTable .. "\n")
  
  --print("Wrote to file: banList = " .. serializedTable)
  
  serializedTable = tableToString(levelList)
  
  f:write("levelList = " .. serializedTable .. "\n")
  
  --print("Wrote to file: levelList = " .. serializedTable)
  
  f:close()
