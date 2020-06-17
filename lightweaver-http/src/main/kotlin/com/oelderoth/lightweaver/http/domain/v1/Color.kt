package com.oelderoth.lightweaver.http.domain.v1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Color {
    @Serializable
    @SerialName("Rgba")
    data class RgbaColor(val red: Int, val green: Int, val blue: Int, val alpha: Int) : Color()

    @Serializable
    @SerialName("Hsla")
    data class HslaColor(val hue: Float, val saturation: Float, val lightness: Float, val alpha: Int) : Color()

    @Serializable
    @SerialName("Hsva")
    data class HsvaColor(val hue: Float, val saturation: Float, val value: Float, val alpha: Int) : Color()
}