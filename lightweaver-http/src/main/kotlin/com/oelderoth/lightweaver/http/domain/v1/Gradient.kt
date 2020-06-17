package com.oelderoth.lightweaver.http.domain.v1

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
sealed class Gradient {
    class EvenGradient(val colors: List<Color>, val easing: EasingFunction) : Gradient()
    class PositionedGradient(val colors: Dictionary<Int, Color>, val easing: EasingFunction) : Gradient()
}