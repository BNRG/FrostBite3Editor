--
--   This is executed very early, and can really only be used to
--   configure settings.
--
--   The settings specified here will override any settings in the
--   legacy settings file pipeline.config
--

core = require "Frost.Core"

require "os"
require "vfs"

Environment = {}

local userName = os.getenv("USERNAME")

if userName then Environment.UserName = userName end

branchName = os.getenv("BRANCH_NAME")

if not branchName then 
	error("Configuration error: BRANCH_NAME environment variable not set -- fix the .diceconfig file for this branch!") 
end

-- Execute configuration and setup scripts

allScriptsRoot = "/Data/Scripts/"
scriptsRoot = allScriptsRoot .. "Pipeline/"

core.import(scriptsRoot .. "Configuration.lua")
