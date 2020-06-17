package com.oelderoth.lightweaver.http.v1.domain

import com.oelderoth.lightweaver.core.devices.SupportedFeature
import kotlinx.serialization.Serializable

@Serializable
class DeviceConfig(val uid: String, val name: String, val firmwareVersion: String, val apiVersion: String, val supportedFeatures: List<SupportedFeature>)