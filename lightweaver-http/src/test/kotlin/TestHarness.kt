
import com.oelderoth.lightweaver.core.colors.HsvaColor
import com.oelderoth.lightweaver.core.colorsource.HsvMeanderColorSource
import com.oelderoth.lightweaver.core.pixeloffsets.RandomPixelOffset
import com.oelderoth.lightweaver.core.pixeloffsets.ScalePixelOffset
import com.oelderoth.lightweaver.http.devices.HttpDevice
import com.oelderoth.lightweaver.http.v1.domain.ColorDto
import com.oelderoth.lightweaver.http.v1.domain.ColorSourceDto
import com.oelderoth.lightweaver.http.v1.domain.EasingFunctionDto
import com.oelderoth.lightweaver.http.v1.domain.PixelOffsetDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.jupiter.api.Test

class TestHarness {
    @Test
    fun test() {
        val json = Json(JsonConfiguration.Default)

        val hslaColor = ColorDto.HslaColorDto(220f,0.5f,0.9f,128)
        val easing = EasingFunctionDto.Linear
        val easing2 = EasingFunctionDto.Reverse(EasingFunctionDto.Mirror(EasingFunctionDto.QuadraticInOut))
        val pixelOffset = PixelOffsetDto.OffsetListPixelOffsetDto(4, listOf(0.5f, 0.2f, 0.5f, 0.1f))

        println(json.stringify(ColorDto.serializer(), hslaColor))
        println(json.stringify(EasingFunctionDto.serializer(), easing))
        println(json.stringify(EasingFunctionDto.serializer(), easing2))
        println(json.stringify(PixelOffsetDto.serializer(), pixelOffset))

        val colorSource = ColorSourceDto.HsvMeanderColorSourceDto(0x5000, ColorSourceDto.HsvMeanderColorSourceDto.HsvMeanderConfigDto(
                ColorDto.HsvaColorDto(10f, 1.0f, 1.0f, 255),
                hue = ColorSourceDto.HsvMeanderColorSourceDto.MeanderRangeDto(50000, 10f),
                value = ColorSourceDto.HsvMeanderColorSourceDto.MeanderRangeDto(25000, 0.6f)
        ), PixelOffsetDto.ScalePixelOffsetDto(13, 0.8f));
        println(json.stringify(ColorSourceDto.serializer(), colorSource))
    }

    @Test
    fun deviceTest() {
        val device = HttpDevice("http://192.168.0.145:80/")
        val colorSource = HsvMeanderColorSource(
            0x0099,
            HsvMeanderColorSource.HsvMeanderConfig(
                    HsvaColor(210f, 1.0f, 1.0f, 255),
                    hue = HsvMeanderColorSource.MeanderRange(50000, 90f),
                    value = HsvMeanderColorSource.MeanderRange(25000, 0.6f)
            ),
            ScalePixelOffset(1, 0.1f)
        )

        val descriptor = device.GetDeviceDescriptor()
        println(descriptor.uid)

        device.SetColorSource(colorSource)

        device.SetBrightness(32);
    }
}