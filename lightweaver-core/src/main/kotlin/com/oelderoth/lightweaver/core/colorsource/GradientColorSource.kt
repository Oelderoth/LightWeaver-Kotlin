package com.oelderoth.lightweaver.core.colorsource

import com.oelderoth.lightweaver.core.colors.Gradient
import com.oelderoth.lightweaver.core.easing.EasingFunction
import com.oelderoth.lightweaver.core.pixeloffsets.PixelOffset

class GradientColorSource(uid: Int, val gradient: Gradient, override val duration: Int, override val loop: Boolean, override val easing: EasingFunction, override val pixelOffsets: PixelOffset) : ColorSource(uid), AnimatedColorSource, AddressableColorSource