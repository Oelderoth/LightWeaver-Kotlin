package com.oelderoth.lightweaver.http.v1.domain

import com.oelderoth.lightweaver.core.colors.Color
import com.oelderoth.lightweaver.core.colors.HslaColor
import com.oelderoth.lightweaver.core.colors.HsvaColor
import com.oelderoth.lightweaver.core.colors.RgbaColor
import com.oelderoth.lightweaver.http.exception.UnsupportedFeatureException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class ColorDto {
    @Serializable
    @SerialName("Rgba")
    data class RgbaColorDto(val red: Int, val green: Int, val blue: Int, val alpha: Int) : ColorDto()

    @Serializable
    @SerialName("Hsla")
    data class HslaColorDto(val hue: Float, val saturation: Float, val lightness: Float, val alpha: Int) : ColorDto()

    @Serializable
    @SerialName("Hsva")
    data class HsvaColorDto(val hue: Float, val saturation: Float, val value: Float, val alpha: Int) : ColorDto()

    companion object {
        fun From(color: Color): ColorDto {
            return when (color) {
                is RgbaColor -> RgbaColorDto(color.red, color.green, color.blue, color.alpha)
                is HslaColor -> HslaColorDto(color.hue, color.saturation, color.lightness, color.alpha)
                is HsvaColor -> HsvaColorDto(color.hue, color.saturation, color.value, color.alpha)
                else -> throw UnsupportedFeatureException(color::class.simpleName!!, Color::class.simpleName!!, 1)
            }
        }
    }
}