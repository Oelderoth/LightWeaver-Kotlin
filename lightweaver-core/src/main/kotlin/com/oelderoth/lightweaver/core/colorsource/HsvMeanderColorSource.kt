package com.oelderoth.lightweaver.core.colorsource

import com.oelderoth.lightweaver.core.colors.Color
import com.oelderoth.lightweaver.core.pixeloffsets.PixelOffset

class HsvMeanderColorSource(uid: String, val meander: HsvMeanderConfig, override val pixelOffsets: PixelOffset) : ColorSource("HsvMeander", uid), AddressableColorSource {
    class MeanderRange(val duration: Int, val distance: Int)
    class HsvMeanderConfig(val color: Color, val hue: MeanderRange, val saturation: MeanderRange, val value: MeanderRange)
}