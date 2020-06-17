package com.oelderoth.lightweaver.core.colorsource

import com.oelderoth.lightweaver.core.easing.EasingFunction

abstract class AnimatedColorSource(val duration: Int, val loop: Boolean, val easing: EasingFunction)