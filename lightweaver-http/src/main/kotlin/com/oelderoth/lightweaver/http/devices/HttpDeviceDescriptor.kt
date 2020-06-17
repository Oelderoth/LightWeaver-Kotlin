package com.oelderoth.lightweaver.http.devices

import com.oelderoth.lightweaver.core.devices.Device
import com.oelderoth.lightweaver.core.devices.DeviceDescriptor
import com.oelderoth.lightweaver.core.devices.SupportedFeature

class HttpDeviceDescriptor(uid: String, name: String, firmwareVersion: String, supportedFeatures: List<SupportedFeature>, val httpApiVersion: String, val url: String) : DeviceDescriptor(uid, name, firmwareVersion, supportedFeatures) {
    override fun GetDevice(): Device {
        return HttpDevice(url)
    }
}