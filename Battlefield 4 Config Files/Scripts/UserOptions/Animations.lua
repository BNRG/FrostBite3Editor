local quality = settings['AnimationQuality']
log:info("setting animation quality : "..quality)

-- IMPORTANT:
-- GameAnimation.TemporalLoddingFirstDistance *must* be slighly larger than the 
-- distance where IK is disabled in the animation project (EnableFootplanting_ESIG) 
-- for all quality levels.

if(quality > 0.8) then
	applySettings([=[

	GameAnimation.TemporalLoddingFirstDistance 10000.0
	GameAnimation.TemporalLoddingSecondDistance 10000.0
	GameAnimation.TemporalLoddingThirdDistance 10000.0
	GameAnimation.TemporalLoddingFourthDistance 10000.0
	GameAnimation.TemporalLoddingFifthDistance 10000.0
	GameAnimation.TemporalLoddingSixthDistance 10000.0

	GameAnimation.TemporalLoddingFirstDeltaTime = 0.02
	GameAnimation.TemporalLoddingSecondDeltaTime = 0.04
	GameAnimation.TemporalLoddingThirdDeltaTime = 0.06
	GameAnimation.TemporalLoddingFourthDeltaTime = 0.08
	GameAnimation.TemporalLoddingFifthDeltaTime = 0.10
	GameAnimation.TemporalLoddingSixthDeltaTime = 0.12

  ]=])
else
	applySettings([=[

	GameAnimation.TemporalLoddingFirstDistance 10000.0
	GameAnimation.TemporalLoddingSecondDistance 10000.0
	GameAnimation.TemporalLoddingThirdDistance 10000.0
	GameAnimation.TemporalLoddingFourthDistance 10000.0
	GameAnimation.TemporalLoddingFifthDistance 10000.0
	GameAnimation.TemporalLoddingSixthDistance 10000.0

	GameAnimation.TemporalLoddingFirstDeltaTime = 0.02
	GameAnimation.TemporalLoddingSecondDeltaTime = 0.04
	GameAnimation.TemporalLoddingThirdDeltaTime = 0.06
	GameAnimation.TemporalLoddingFourthDeltaTime = 0.08
	GameAnimation.TemporalLoddingFifthDeltaTime = 0.10
	GameAnimation.TemporalLoddingSixthDeltaTime = 0.12

  ]=])
end