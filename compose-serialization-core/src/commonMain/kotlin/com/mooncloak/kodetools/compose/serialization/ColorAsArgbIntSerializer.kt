package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object ColorAsArgbIntSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor
        get() = Int.serializer().descriptor

    override fun deserialize(decoder: Decoder): Color {
        val value = decoder.decodeInt()

        return Color(value)
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeInt(value.toArgb())
    }
}

public fun Color.Companion.argbIntSerializer(): KSerializer<Color> =
    ColorAsArgbIntSerializer
