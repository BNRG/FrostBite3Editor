require "vfs"
require "os"

function Session_PlayerJoined(id)

  print("Session_PlayerJoined called: Player " .. id .. " joined")
  
end

function Session_PlayerLeft(id)

  print("Session_PlayerLeft called: Player " .. id .. " left")

end

function Session_PlayerAuthenticated(id)

  print("Session_PlayerJoined called: Player " .. id .. " joined")
  
  if (banList[id] ~= nil) then
	print("Player " .. banList[id].name .. " is banned")
	kickPlayer(id)
  end
  
end