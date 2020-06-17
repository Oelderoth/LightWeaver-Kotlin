package com.oelderoth.lightweaver.http.devices

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.oelderoth.lightweaver.core.colors.*
import com.oelderoth.lightweaver.core.colorsource.*
import com.oelderoth.lightweaver.core.devices.Device
import com.oelderoth.lightweaver.core.devices.DeviceDescriptor
import com.oelderoth.lightweaver.core.easing.EasingFunction
import com.oelderoth.lightweaver.core.pixeloffsets.OffsetListPixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.PixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.RandomPixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.ScalePixelOffset
import com.oelderoth.lightweaver.http.v1.client.LightWeaverHttpV1Api
import com.oelderoth.lightweaver.http.v1.domain.BrightnessPayload
import com.oelderoth.lightweaver.http.v1.domain.Gradient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Retrofit

class HttpDevice(val url: String): Device {
    private val lightweaverClient by lazy {
        val contentType = MediaType.get("application/json")
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(Json(JsonConfiguration.Stable).asConverterFactory(contentType))
                .build()
        retrofit.create(LightWeaverHttpV1Api::class.java)
    }

    override fun GetDeviceDescriptor(): DeviceDescriptor {
        val config = ExecuteCallWithResult(lightweaverClient.config())
        return HttpDeviceDescriptor(config.uid, config.name, config.firmwareVersion, config.supportedFeatures, config.apiVersion, url)
    }

    override fun GetBrightness(): Int {
        val state = ExecuteCallWithResult(lightweaverClient.state())
        return state.brightness
    }

    override fun SetBrightness(brightness: Int) {
        ExecuteCall(lightweaverClient.setBrightness(BrightnessPayload(brightness)))
    }

    override fun SetColorSource(source: ColorSource) {
        val colorSourcePayload: com.oelderoth.lightweaver.http.v1.domain.ColorSource = mapColorSource(source)
        ExecuteCall(lightweaverClient.setColorSource(colorSourcePayload))
    }

    private fun mapColorSource(source: ColorSource): com.oelderoth.lightweaver.http.v1.domain.ColorSource {
        return when (source) {
            is SolidColorSource -> com.oelderoth.lightweaver.http.v1.domain.ColorSource.SolidColorSource(source.uid, mapColor(source.color))
            is FadeColorSource -> com.oelderoth.lightweaver.http.v1.domain.ColorSource.FadeColorSource(source.uid, mapColor(source.start), mapColor(source.end), source.duration, source.loop, mapEasing(source.easing))
            is GradientColorSource -> com.oelderoth.lightweaver.http.v1.domain.ColorSource.GradientColorSource(source.uid, mapGradient(source.gradient), source.duration, source.loop, mapEasing(source.easing), mapPixelOffsets(source.pixelOffsets))
            is HsvMeanderColorSource -> com.oelderoth.lightweaver.http.v1.domain.ColorSource.HsvMeanderColorSource(source.uid,
                    com.oelderoth.lightweaver.http.v1.domain.ColorSource.HsvMeanderColorSource.HsvMeanderConfig(mapColor(source.meander.color), mapMeanderRange(source.meander.hue), mapMeanderRange(source.meander.saturation), mapMeanderRange(source.meander.value)),
                    mapPixelOffsets(source.pixelOffsets))
            is OverlayColorSource -> com.oelderoth.lightweaver.http.v1.domain.ColorSource.OverlayColorSource(source.uid, mapColorSource(source.background), mapColorSource(source.overlay))
            else -> throw RuntimeException("Unsupported Color Source")
        }
    }

    private fun mapMeanderRange(range: HsvMeanderColorSource.MeanderRange?): com.oelderoth.lightweaver.http.v1.domain.ColorSource.HsvMeanderColorSource.MeanderRange? {
        if (range == null) return null
        return com.oelderoth.lightweaver.http.v1.domain.ColorSource.HsvMeanderColorSource.MeanderRange(range.duration, range.distance)
    }

    private fun mapPixelOffsets(pixelOffsets: PixelOffset): com.oelderoth.lightweaver.http.v1.domain.PixelOffset {
        return when (pixelOffsets) {
            is ScalePixelOffset -> com.oelderoth.lightweaver.http.v1.domain.PixelOffset.ScalePixelOffset(pixelOffsets.groupSize, pixelOffsets.scale)
            is RandomPixelOffset -> com.oelderoth.lightweaver.http.v1.domain.PixelOffset.RandomPixelOffset(pixelOffsets.groupSize)
            is OffsetListPixelOffset -> com.oelderoth.lightweaver.http.v1.domain.PixelOffset.OffsetListPixelOffset(pixelOffsets.groupSize, pixelOffsets.offsets)
            else -> throw RuntimeException("Unsupported Pixel Offset")
        }
    }

    private fun mapGradient(gradient: com.oelderoth.lightweaver.core.colors.Gradient): Gradient {
        return when (gradient) {
            is EvenGradient -> Gradient.EvenGradient(gradient.colors.map(this::mapColor), mapEasing(gradient.easing))
            is PositionedGradient -> Gradient.PositionedGradient(gradient.colors.mapValues { entry -> mapColor(entry.value) }, mapEasing(gradient.easing))
            else -> throw RuntimeException("Unsupported Gradient Type")
        }
    }

    private fun mapEasing(easing: EasingFunction): com.oelderoth.lightweaver.http.v1.domain.EasingFunction {
        return when (easing) {
            EasingFunction.Linear -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.Linear
            EasingFunction.QuadraticIn -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuadraticIn
            EasingFunction.QuadraticOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuadraticOut
            EasingFunction.QuadraticInOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuadraticInOut
            EasingFunction.CubicIn -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.CubicIn
            EasingFunction.CubicOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.CubicOut
            EasingFunction.CubicInOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.CubicInOut
            EasingFunction.QuarticIn -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuarticIn
            EasingFunction.QuarticOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuarticOut
            EasingFunction.QuarticInOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuarticInOut
            EasingFunction.QuinticIn -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuinticIn
            EasingFunction.QuinticOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuinticOut
            EasingFunction.QuinticInOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.QuinticInOut
            EasingFunction.ExponentialIn -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.ExponentialIn
            EasingFunction.ExponentialOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.ExponentialOut
            EasingFunction.ExponentialInOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.ExponentialInOut
            EasingFunction.SinusoidalIn -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.SinusoidalIn
            EasingFunction.SinusoidalOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.SinusoidalOut
            EasingFunction.SinusoidalInOut -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.SinusoidalInOut
            is EasingFunction.MirrorEasingFunction -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.Mirror(mapEasing(easing.easing))
            is EasingFunction.ReverseEasingFunction -> com.oelderoth.lightweaver.http.v1.domain.EasingFunction.Reverse(mapEasing(easing.easing))
            else -> throw RuntimeException("Unsupported Easing Function")
        }

    }

    private fun mapColor(color: Color): com.oelderoth.lightweaver.http.v1.domain.Color {
        return when (color) {
            is RgbaColor -> com.oelderoth.lightweaver.http.v1.domain.Color.RgbaColor(color.red, color.green, color.blue, color.alpha)
            is HsvaColor -> com.oelderoth.lightweaver.http.v1.domain.Color.HsvaColor(color.hue, color.saturation, color.value, color.alpha)
            is HslaColor -> com.oelderoth.lightweaver.http.v1.domain.Color.HslaColor(color.hue, color.saturation, color.lightness, color.alpha)
            else -> throw RuntimeException("Unsupported Color Type")
        }
    }

    private fun <T> ExecuteCall(call: Call<T>) {
        val result = call.execute()
        if (!result.isSuccessful) {
            println(result.message())
            throw RuntimeException("Lightweaver Api Exception")
        }
    }

    private fun <T> ExecuteCallWithResult(call: Call<T>): T {
        val result = call.execute()
        if (!result.isSuccessful) {
            println(result.message())
            throw RuntimeException("Lightweaver Api Exception")
        }
        return result.body()!!
    }
}