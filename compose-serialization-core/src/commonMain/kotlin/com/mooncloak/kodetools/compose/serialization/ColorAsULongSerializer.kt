package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object ColorAsULongSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor
        get() = ULong.serializer().descriptor

    override fun deserialize(decoder: Decoder): Color {
        val value = decoder.decodeSerializableValue(
            deserializer = ULong.serializer()
        )

        return Color(value)
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeSerializableValue(
            serializer = ULong.serializer(),
            value = value.value
        )
    }
}

public fun Color.Companion.uLongSerializer(): KSerializer<Color> =
    ColorAsULongSerializer
