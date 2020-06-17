package com.oelderoth.lightweaver.http.domain.v1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class EasingFunction() {
    @Serializable
    @SerialName("Linear")
    object Linear : EasingFunction()

    @Serializable
    @SerialName("QuadraticIn")
    object QuadraticIn: EasingFunction()

    @Serializable
    @SerialName("QuadraticOut")
    object QuadraticOut: EasingFunction()

    @Serializable
    @SerialName("QuadraticInOut")
    object QuadraticInOut: EasingFunction()

    @Serializable
    @SerialName("CubicIn")
    object CubicIn: EasingFunction()

    @Serializable
    @SerialName("CubicOut")
    object CubicOut: EasingFunction()

    @Serializable
    @SerialName("CubicInOut")
    object CubicInOut: EasingFunction()

    @Serializable
    @SerialName("QuarticIn")
    object QuarticIn: EasingFunction()

    @Serializable
    @SerialName("QuarticOut")
    object QuarticOut: EasingFunction()

    @Serializable
    @SerialName("QuarticInOut")
    object QuarticInOut: EasingFunction()

    @Serializable
    @SerialName("QuinticIn")
    object QuinticIn: EasingFunction()

    @Serializable
    @SerialName("QuinticOut")
    object QuinticOut: EasingFunction()

    @Serializable
    @SerialName("QuinticInOut")
    object QuinticInOut: EasingFunction()

    @Serializable
    @SerialName("ExponentialIn")
    object ExponentialIn: EasingFunction()

    @Serializable
    @SerialName("ExponentialOut")
    object ExponentialOut: EasingFunction()

    @Serializable
    @SerialName("ExponentialInOut")
    object ExponentialInOut: EasingFunction()

    @Serializable
    @SerialName("SinusoidalIn")
    object SinusoidalIn: EasingFunction()

    @Serializable
    @SerialName("SinusoidalOut")
    object SinusoidalOut: EasingFunction()

    @Serializable
    @SerialName("SinusoidalInOut")
    object SinusoidalInOut: EasingFunction()

    @Serializable
    @SerialName("Reverse")
    class Reverse(val easing: EasingFunction): EasingFunction()

    @Serializable
    @SerialName("Mirror")
    class Mirror(val easing: EasingFunction): EasingFunction()
}