print("Loading scripts...")

serverSaveFilePath = "serverAdminSave.lua"

dofile("Scripts/Server/Startup.lua")
dofile("Scripts/Server/Shutdown.lua")
dofile("Scripts/Server/Server.lua")
dofile("Scripts/Server/Session.lua")

print("Done loading scripts.")