package com.oelderoth.lightweaver.core.colorsource

import com.oelderoth.lightweaver.core.colors.Color
import com.oelderoth.lightweaver.core.pixeloffsets.PixelOffset

class HsvMeanderColorSource(uid: Int, val meander: HsvMeanderConfig, override val pixelOffsets: PixelOffset) : ColorSource(uid), AddressableColorSource {
    class MeanderRange(val duration: Int, val distance: Float)
    class HsvMeanderConfig(val color: Color, val hue: MeanderRange? = null, val saturation: MeanderRange? = null, val value: MeanderRange? = null)
}