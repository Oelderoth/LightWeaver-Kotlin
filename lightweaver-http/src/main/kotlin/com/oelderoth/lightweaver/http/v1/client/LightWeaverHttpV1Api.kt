package com.oelderoth.lightweaver.http.v1.client

import com.oelderoth.lightweaver.http.v1.domain.BrightnessPayload
import com.oelderoth.lightweaver.http.v1.domain.ColorSource
import com.oelderoth.lightweaver.http.v1.domain.DeviceConfig
import com.oelderoth.lightweaver.http.v1.domain.DeviceState
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface LightWeaverHttpV1Api {
    @GET("/lightWeaver/alive")
    fun alive(): Call<Void>

    @GET("/lightWeaver/config")
    fun config(): Call<DeviceConfig>

    @GET("/lightWeaver/state")
    fun state(): Call<DeviceState>

    @POST("/lightWeaver/setBrightness")
    @Headers("Content-Type: application/json")
    fun setBrightness(@Body brightness: BrightnessPayload): Call<Void>

    @POST("/lightWeaver/setColorSource")
    @Headers("Content-Type: application/json")
    fun setColorSource(@Body colorSource: ColorSource): Call<Void>

    @POST("/lightWeaver/clearColorSource")
    @Headers("Content-Type: application/json")
    fun clearColorSource(): Call<Void>
}