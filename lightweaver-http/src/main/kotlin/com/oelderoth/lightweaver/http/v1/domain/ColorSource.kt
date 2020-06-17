package com.oelderoth.lightweaver.http.v1.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ColorSource {
    @Serializable
    @SerialName("Solid")
    class SolidColorSource(val uid: Int, val color: Color) : ColorSource()

    @Serializable
    @SerialName("Fade")
    class FadeColorSource(val uid: Int, val start: Color, val end: Color, val duration: Int, val loop: Boolean, val easing: EasingFunction) : ColorSource()

    @Serializable
    @SerialName("Gradient")
    class GradientColorSource(val uid: Int, val gradient: Gradient, val duration: Int, val loop: Boolean, val easing: EasingFunction, val pixelOffsets: PixelOffset) : ColorSource()

    @Serializable
    @SerialName("HsvMeander")
    class HsvMeanderColorSource(val uid: Int, val meander: HsvMeanderConfig, val pixelOffsets: PixelOffset) : ColorSource() {
        @Serializable class MeanderRange(val duration: Int, val distance: Float)
        @Serializable class HsvMeanderConfig(val color: Color, val hue: MeanderRange? = null, val saturation: MeanderRange? = null, val value: MeanderRange? = null)
    }

    @Serializable
    @SerialName("Overlay")
    class OverlayColorSource(val uid: Int, val background: ColorSource, val overlay: ColorSource): ColorSource()

}