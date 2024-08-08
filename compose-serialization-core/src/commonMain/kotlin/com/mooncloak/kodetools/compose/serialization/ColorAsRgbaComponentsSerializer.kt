package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class RgbaColorSurrogate internal constructor(
    @SerialName(value = "red") val red: Float,
    @SerialName(value = "green") val green: Float,
    @SerialName(value = "blue") val blue: Float,
    @SerialName(value = "alpha") val alpha: Float
)

public data object ColorAsRgbaComponentsSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor
        get() = RgbaColorSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): Color {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = RgbaColorSurrogate.serializer()
        )

        return Color(
            red = surrogate.red,
            green = surrogate.green,
            blue = surrogate.blue,
            alpha = surrogate.alpha
        )
    }

    override fun serialize(encoder: Encoder, value: Color) {
        val surrogate = RgbaColorSurrogate(
            red = value.red,
            green = value.green,
            blue = value.blue,
            alpha = value.alpha
        )

        encoder.encodeSerializableValue(
            serializer = RgbaColorSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun Color.Companion.rgbaSerializer(): KSerializer<Color> =
    ColorAsRgbaComponentsSerializer
