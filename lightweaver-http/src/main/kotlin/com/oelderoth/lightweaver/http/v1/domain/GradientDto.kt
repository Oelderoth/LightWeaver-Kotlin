package com.oelderoth.lightweaver.http.v1.domain

import com.oelderoth.lightweaver.core.colors.EvenGradient
import com.oelderoth.lightweaver.core.colors.Gradient
import com.oelderoth.lightweaver.core.colors.PositionedGradient
import com.oelderoth.lightweaver.http.exception.UnsupportedFeatureException
import kotlinx.serialization.Serializable

@Serializable
internal sealed class GradientDto {
    class EvenGradientDto(val colors: List<ColorDto>, val easing: EasingFunctionDto) : GradientDto()
    class PositionedGradientDto(val colors: Map<Int, ColorDto>, val easing: EasingFunctionDto) : GradientDto()

    companion object {
        fun From(gradient: Gradient): GradientDto {
            return when (gradient) {
                is EvenGradient -> EvenGradientDto(gradient.colors.map(ColorDto.Companion::From), EasingFunctionDto.From(gradient.easing))
                is PositionedGradient -> PositionedGradientDto(gradient.colors.mapValues { entry -> ColorDto.From(entry.value) }, EasingFunctionDto.From(gradient.easing))
                else -> throw UnsupportedFeatureException(gradient::class.simpleName!!, Gradient::class.simpleName!!, 1)
            }
        }
    }
}