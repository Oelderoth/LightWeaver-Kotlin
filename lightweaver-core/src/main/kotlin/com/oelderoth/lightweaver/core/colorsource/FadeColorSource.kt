package com.oelderoth.lightweaver.core.colorsource

import com.oelderoth.lightweaver.core.colors.Color
import com.oelderoth.lightweaver.core.easing.EasingFunction

class FadeColorSource(uid: String, val start: Color, val end: Color, override val duration: Int, override val loop: Boolean, override val easing: EasingFunction) : ColorSource("Fade", uid), AnimatedColorSource