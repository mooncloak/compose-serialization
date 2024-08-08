package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.TextMotion
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object TextMotionAsStringSerializer : KSerializer<TextMotion> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextMotion {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "static" -> TextMotion.Static
            "animated" -> TextMotion.Animated
            else -> error("Unsupported TextMotion value '$value'. Use either 'static' or 'animated'.")
        }
    }

    override fun serialize(encoder: Encoder, value: TextMotion) {
        val stringValue = when (value) {
            TextMotion.Static -> "static"
            TextMotion.Animated -> "animated"
            else -> error("Unsupported TextMotion value '$value'. Use either 'static' or 'animated'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun TextMotion.Companion.serializer(): KSerializer<TextMotion> =
    TextMotionAsStringSerializer
