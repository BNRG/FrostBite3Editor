local quality = settings['PhysicsQuality']
log:info("setting physics quality : "..quality)
if(quality > 0.8) then
	applySettings([=[
			Physics.EnableFollowWheelRaycasts true
			Physics.EnableASyncWheelRaycasts true
  ]=])
else
	applySettings([=[
			Physics.EnableFollowWheelRaycasts false
			Physics.EnableASyncWheelRaycasts true
  ]=])
end