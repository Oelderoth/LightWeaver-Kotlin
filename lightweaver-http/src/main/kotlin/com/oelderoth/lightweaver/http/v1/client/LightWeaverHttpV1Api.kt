package com.oelderoth.lightweaver.http.v1.client

import com.oelderoth.lightweaver.http.v1.domain.BrightnessDto
import com.oelderoth.lightweaver.http.v1.domain.ColorSourceDto
import com.oelderoth.lightweaver.http.v1.domain.DeviceConfigDto
import com.oelderoth.lightweaver.http.v1.domain.DeviceStateDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface LightWeaverHttpV1Api {
    @GET("/lightWeaver/alive")
    fun alive(): Call<Void>

    @GET("/lightWeaver/config")
    fun config(): Call<DeviceConfigDto>

    @GET("/lightWeaver/state")
    fun state(): Call<DeviceStateDto>

    @POST("/lightWeaver/setBrightness")
    @Headers("Content-Type: application/json")
    fun setBrightness(@Body brightness: BrightnessDto): Call<Void>

    @POST("/lightWeaver/setColorSource")
    @Headers("Content-Type: application/json")
    fun setColorSource(@Body colorSource: ColorSourceDto): Call<Void>

    @POST("/lightWeaver/clearColorSource")
    @Headers("Content-Type: application/json")
    fun clearColorSource(): Call<Void>
}