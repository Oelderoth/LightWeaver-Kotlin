package com.oelderoth.lightweaver.http.devices

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.oelderoth.lightweaver.core.colorsource.ColorSource
import com.oelderoth.lightweaver.core.devices.Device
import com.oelderoth.lightweaver.core.devices.DeviceDescriptor
import com.oelderoth.lightweaver.http.v1.client.LightWeaverHttpV1Api
import com.oelderoth.lightweaver.http.v1.domain.BrightnessDto
import com.oelderoth.lightweaver.http.v1.domain.ColorSourceDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
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
        val response = lightweaverClient.config().execute()
        // TODO: Error Handling
        val config = response.body()!!
        return HttpDeviceDescriptor(config.uid, config.name, config.firmwareVersion, config.supportedFeatures, config.apiVersion, url)
    }

    override fun GetBrightness(): Int {
        val response = lightweaverClient.state().execute()
        // TODO: Error Handling
        val state = response.body()!!
        return state.brightness
    }

    override fun SetBrightness(brightness: Int) {
        lightweaverClient.setBrightness(BrightnessDto(brightness)).execute()
    }

    override fun SetColorSource(source: ColorSource) {
        val colorSourcePayload: ColorSourceDto = ColorSourceDto.From(source)
        lightweaverClient.setColorSource(colorSourcePayload).execute()
    }
}