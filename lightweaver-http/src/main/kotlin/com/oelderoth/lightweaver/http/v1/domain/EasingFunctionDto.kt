package com.oelderoth.lightweaver.http.v1.domain

import com.oelderoth.lightweaver.core.colors.Color
import com.oelderoth.lightweaver.core.colors.HslaColor
import com.oelderoth.lightweaver.core.colors.HsvaColor
import com.oelderoth.lightweaver.core.colors.RgbaColor
import com.oelderoth.lightweaver.core.easing.EasingFunction
import com.oelderoth.lightweaver.http.exception.UnsupportedFeatureException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class EasingFunctionDto() {
    @Serializable
    @SerialName("Linear")
    object Linear : EasingFunctionDto()

    @Serializable
    @SerialName("QuadraticIn")
    object QuadraticIn: EasingFunctionDto()

    @Serializable
    @SerialName("QuadraticOut")
    object QuadraticOut: EasingFunctionDto()

    @Serializable
    @SerialName("QuadraticInOut")
    object QuadraticInOut: EasingFunctionDto()

    @Serializable
    @SerialName("CubicIn")
    object CubicIn: EasingFunctionDto()

    @Serializable
    @SerialName("CubicOut")
    object CubicOut: EasingFunctionDto()

    @Serializable
    @SerialName("CubicInOut")
    object CubicInOut: EasingFunctionDto()

    @Serializable
    @SerialName("QuarticIn")
    object QuarticIn: EasingFunctionDto()

    @Serializable
    @SerialName("QuarticOut")
    object QuarticOut: EasingFunctionDto()

    @Serializable
    @SerialName("QuarticInOut")
    object QuarticInOut: EasingFunctionDto()

    @Serializable
    @SerialName("QuinticIn")
    object QuinticIn: EasingFunctionDto()

    @Serializable
    @SerialName("QuinticOut")
    object QuinticOut: EasingFunctionDto()

    @Serializable
    @SerialName("QuinticInOut")
    object QuinticInOut: EasingFunctionDto()

    @Serializable
    @SerialName("ExponentialIn")
    object ExponentialIn: EasingFunctionDto()

    @Serializable
    @SerialName("ExponentialOut")
    object ExponentialOut: EasingFunctionDto()

    @Serializable
    @SerialName("ExponentialInOut")
    object ExponentialInOut: EasingFunctionDto()

    @Serializable
    @SerialName("SinusoidalIn")
    object SinusoidalIn: EasingFunctionDto()

    @Serializable
    @SerialName("SinusoidalOut")
    object SinusoidalOut: EasingFunctionDto()

    @Serializable
    @SerialName("SinusoidalInOut")
    object SinusoidalInOut: EasingFunctionDto()

    @Serializable
    @SerialName("Reverse")
    class Reverse(val easing: EasingFunctionDto): EasingFunctionDto()

    @Serializable
    @SerialName("Mirror")
    class Mirror(val easing: EasingFunctionDto): EasingFunctionDto()

    companion object {
        fun From(easing: EasingFunction): EasingFunctionDto {
            return when (easing) {
                is EasingFunction.Linear -> Linear
                is EasingFunction.QuadraticIn -> QuadraticIn
                is EasingFunction.QuadraticOut -> QuadraticOut
                is EasingFunction.QuadraticInOut -> QuadraticInOut
                is EasingFunction.CubicIn -> CubicIn
                is EasingFunction.CubicOut -> CubicOut
                is EasingFunction.CubicInOut -> CubicInOut
                is EasingFunction.QuarticIn -> QuarticIn
                is EasingFunction.QuarticOut -> QuarticOut
                is EasingFunction.QuarticInOut -> QuarticInOut
                is EasingFunction.QuinticIn -> QuinticIn
                is EasingFunction.QuinticOut -> QuinticOut
                is EasingFunction.QuinticInOut -> QuinticInOut
                is EasingFunction.ExponentialIn -> ExponentialIn
                is EasingFunction.ExponentialOut -> ExponentialOut
                is EasingFunction.ExponentialInOut -> ExponentialInOut
                is EasingFunction.SinusoidalIn -> SinusoidalIn
                is EasingFunction.SinusoidalOut -> SinusoidalOut
                is EasingFunction.SinusoidalInOut -> SinusoidalInOut
                is EasingFunction.Reverse -> Reverse(From(easing.easing))
                is EasingFunction.Mirror -> Mirror(From(easing.easing))
                else -> throw UnsupportedFeatureException(easing::class.simpleName!!, EasingFunction::class.simpleName!!, 1)
            }
        }
    }
}