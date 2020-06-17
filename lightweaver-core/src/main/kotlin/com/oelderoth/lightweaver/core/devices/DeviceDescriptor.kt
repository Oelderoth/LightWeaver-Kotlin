package com.oelderoth.lightweaver.core.devices

abstract class DeviceDescriptor(val uid: String, val name: String, val firmwareVersion: String, val supportedFeatures: List<SupportedFeature>) {
    abstract fun GetDevice(): Device;
}