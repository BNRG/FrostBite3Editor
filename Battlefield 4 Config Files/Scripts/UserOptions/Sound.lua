local quality = settings['AudioQuality']
log:info("setting sound quality : "..quality)
if(quality > 0.8) then
	applySettings([=[
			Audio.MaxAudibleSoundCount 15
			Audio.AudioCoreCpuLoadLimit 50
  ]=])
else
	applySettings([=[
			Audio.MaxAudibleSoundCount 8
			Audio.AudioCoreCpuLoadLimit 30
  ]=])
end