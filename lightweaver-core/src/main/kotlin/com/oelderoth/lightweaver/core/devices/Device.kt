package com.oelderoth.lightweaver.core.devices

import com.oelderoth.lightweaver.core.colorsource.ColorSource

interface Device {
    fun GetDeviceDescriptor(): DeviceDescriptor;
    fun GetBrightness(): Int;
    fun SetBrightness(brightness: Int);
    fun SetColorSource(source: ColorSource);
}