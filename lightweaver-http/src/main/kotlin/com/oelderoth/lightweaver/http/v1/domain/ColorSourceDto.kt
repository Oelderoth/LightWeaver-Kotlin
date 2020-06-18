package com.oelderoth.lightweaver.http.v1.domain

import com.oelderoth.lightweaver.core.colorsource.*
import com.oelderoth.lightweaver.http.exception.UnsupportedFeatureException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class ColorSourceDto {
    @Serializable
    @SerialName("Solid")
    class SolidColorSourceDto(val uid: Int, val color: ColorDto) : ColorSourceDto()

    @Serializable
    @SerialName("Fade")
    class FadeColorSourceDto(val uid: Int, val start: ColorDto, val end: ColorDto, val duration: Int, val loop: Boolean, val easing: EasingFunctionDto) : ColorSourceDto()

    @Serializable
    @SerialName("Gradient")
    class GradientColorSourceDto(val uid: Int, val gradient: GradientDto, val duration: Int, val loop: Boolean, val easing: EasingFunctionDto, val pixelOffsets: PixelOffsetDto) : ColorSourceDto()

    @Serializable
    @SerialName("HsvMeander")
    class HsvMeanderColorSourceDto(val uid: Int, val meander: HsvMeanderConfigDto, val pixelOffsets: PixelOffsetDto) : ColorSourceDto() {
        @Serializable class MeanderRangeDto(val duration: Int, val distance: Float) {
            companion object {
                fun From(meanderRange: HsvMeanderColorSource.MeanderRange): MeanderRangeDto? {
                    return MeanderRangeDto(meanderRange.duration, meanderRange.distance)
                }
            }
        }

        @Serializable class HsvMeanderConfigDto(val color: ColorDto, val hue: MeanderRangeDto? = null, val saturation: MeanderRangeDto? = null, val value: MeanderRangeDto? = null) {
            companion object {
                fun From(meanderConfig: HsvMeanderColorSource.HsvMeanderConfig): HsvMeanderConfigDto {
                    return HsvMeanderConfigDto(ColorDto.From(meanderConfig.color),
                            meanderConfig.hue?.let { MeanderRangeDto.From(it) },
                            meanderConfig.saturation?.let { MeanderRangeDto.From(it) },
                            meanderConfig.value?.let { MeanderRangeDto.From(it) })
                }
            }
        }
    }

    @Serializable
    @SerialName("Overlay")
    class OverlayColorSourceDto(val uid: Int, val background: ColorSourceDto, val overlay: ColorSourceDto): ColorSourceDto()

    companion object {
        fun From(colorSource: ColorSource): ColorSourceDto {
            return when (colorSource) {
                is SolidColorSource -> SolidColorSourceDto(colorSource.uid, ColorDto.From(colorSource.color))
                is FadeColorSource -> FadeColorSourceDto(colorSource.uid, ColorDto.From(colorSource.start), ColorDto.From(colorSource.end), colorSource.duration, colorSource.loop, EasingFunctionDto.From(colorSource.easing))
                is GradientColorSource -> GradientColorSourceDto(colorSource.uid, GradientDto.From(colorSource.gradient), colorSource.duration, colorSource.loop, EasingFunctionDto.From(colorSource.easing), PixelOffsetDto.From(colorSource.pixelOffsets))
                is HsvMeanderColorSource -> HsvMeanderColorSourceDto(colorSource.uid, HsvMeanderColorSourceDto.HsvMeanderConfigDto.From(colorSource.meander), PixelOffsetDto.From(colorSource.pixelOffsets))
                is OverlayColorSource -> OverlayColorSourceDto(colorSource.uid, From(colorSource.background), From(colorSource.overlay))
                else -> throw UnsupportedFeatureException(colorSource::class.simpleName!!, ColorSource::class.simpleName!!, 1)
            }
        }
    }
}