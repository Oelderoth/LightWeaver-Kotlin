package com.oelderoth.lightweaver.http.v1.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
sealed class Gradient {
    class EvenGradient(val colors: List<Color>, val easing: EasingFunction) : Gradient()
    class PositionedGradient(val colors: Map<Int, Color>, val easing: EasingFunction) : Gradient()
}