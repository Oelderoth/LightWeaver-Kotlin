
import com.oelderoth.lightweaver.core.colors.HsvaColor
import com.oelderoth.lightweaver.core.colorsource.HsvMeanderColorSource
import com.oelderoth.lightweaver.core.pixeloffsets.RandomPixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.ScalePixelOffset
import com.oelderoth.lightweaver.http.devices.HttpDevice
import com.oelderoth.lightweaver.http.v1.domain.Color
import com.oelderoth.lightweaver.http.v1.domain.ColorSource
import com.oelderoth.lightweaver.http.v1.domain.EasingFunction
import com.oelderoth.lightweaver.http.v1.domain.PixelOffset
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.jupiter.api.Test

class TestHarness {
    @Test
    fun test() {
        val json = Json(JsonConfiguration.Default)

        val hslaColor = Color.HslaColor(220f,0.5f,0.9f,128)
        val easing = EasingFunction.Linear
        val easing2 = EasingFunction.Reverse(EasingFunction.Mirror(EasingFunction.QuadraticInOut))
        val pixelOffset = PixelOffset.OffsetListPixelOffset(4, listOf(0.5f, 0.2f, 0.5f, 0.1f))

        println(json.stringify(Color.serializer(), hslaColor))
        println(json.stringify(EasingFunction.serializer(), easing))
        println(json.stringify(EasingFunction.serializer(), easing2))
        println(json.stringify(PixelOffset.serializer(), pixelOffset))

        val colorSource = ColorSource.HsvMeanderColorSource(0x5000, ColorSource.HsvMeanderColorSource.HsvMeanderConfig(
                Color.HsvaColor(10f, 1.0f, 1.0f, 255),
                hue = ColorSource.HsvMeanderColorSource.MeanderRange(50000, 10f),
                value = ColorSource.HsvMeanderColorSource.MeanderRange(25000, 0.6f)
        ), PixelOffset.ScalePixelOffset(13, 0.8f));
        println(json.stringify(ColorSource.serializer(), colorSource))
    }

    @Test
    fun deviceTest() {
        val device = HttpDevice("http://192.168.0.145:80/")
        val colorSource = HsvMeanderColorSource(
            0x0099,
            HsvMeanderColorSource.HsvMeanderConfig(
                    HsvaColor(210f, 1.0f, 1.0f, 255),
                    hue = HsvMeanderColorSource.MeanderRange(50000, 10f),
                    value = HsvMeanderColorSource.MeanderRange(25000, 0.6f)
            ),
            RandomPixelOffset(2)
        )

        val descriptor = device.GetDeviceDescriptor()
        println(descriptor.uid)

        device.SetColorSource(colorSource)
    }
}