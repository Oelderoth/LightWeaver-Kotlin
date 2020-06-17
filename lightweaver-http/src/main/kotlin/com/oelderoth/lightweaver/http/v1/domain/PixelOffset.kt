package com.oelderoth.lightweaver.http.v1.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PixelOffset() {
    @Serializable
    @SerialName("Scale")
    class ScalePixelOffset(val groupSize: Int, val scale: Float): PixelOffset()

    @Serializable
    @SerialName("Random")
    class RandomPixelOffset(val groupSize: Int): PixelOffset()

    @Serializable
    @SerialName("OffsetList")
    class OffsetListPixelOffset(val groupSize: Int, val offsets: List<Float>): PixelOffset()

}