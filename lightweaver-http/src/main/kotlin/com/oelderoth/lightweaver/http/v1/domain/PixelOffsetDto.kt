package com.oelderoth.lightweaver.http.v1.domain

import com.oelderoth.lightweaver.core.colors.EvenGradient
import com.oelderoth.lightweaver.core.colors.Gradient
import com.oelderoth.lightweaver.core.colors.PositionedGradient
import com.oelderoth.lightweaver.core.pixeloffsets.OffsetListPixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.PixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.RandomPixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.ScalePixelOffset
import com.oelderoth.lightweaver.http.exception.UnsupportedFeatureException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class PixelOffsetDto() {
    @Serializable
    @SerialName("Scale")
    class ScalePixelOffsetDto(val groupSize: Int, val scale: Float): PixelOffsetDto()

    @Serializable
    @SerialName("Random")
    class RandomPixelOffsetDto(val groupSize: Int): PixelOffsetDto()

    @Serializable
    @SerialName("OffsetList")
    class OffsetListPixelOffsetDto(val groupSize: Int, val offsets: List<Float>): PixelOffsetDto()

    companion object {
        fun From(pixelOffsets: PixelOffset): PixelOffsetDto {
            return when (pixelOffsets) {
                is ScalePixelOffset -> ScalePixelOffsetDto(pixelOffsets.groupSize, pixelOffsets.scale)
                is RandomPixelOffset -> RandomPixelOffsetDto(pixelOffsets.groupSize)
                is OffsetListPixelOffset -> OffsetListPixelOffsetDto(pixelOffsets.groupSize, pixelOffsets.offsets)
                else -> throw UnsupportedFeatureException(pixelOffsets::class.simpleName!!, PixelOffset::class.simpleName!!, 1)
            }
        }
    }
}