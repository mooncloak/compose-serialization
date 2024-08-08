package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.TextAlign
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object TextAlignAsStringSerializer : KSerializer<TextAlign> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextAlign {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "left" -> TextAlign.Left
            "right" -> TextAlign.Right
            "center" -> TextAlign.Center
            "justify" -> TextAlign.Justify
            "start" -> TextAlign.Start
            "end" -> TextAlign.End
            "unspecified" -> TextAlign.Unspecified
            else -> error("Unsupported TextAlign value '$value'. Use either 'left', 'right', 'center', 'justify', 'start', 'end', or 'unspecified'.")
        }
    }

    override fun serialize(encoder: Encoder, value: TextAlign) {
        val stringValue = when (value) {
            TextAlign.Left -> "left"
            TextAlign.Right -> "right"
            TextAlign.Center -> "center"
            TextAlign.Justify -> "justify"
            TextAlign.Start -> "start"
            TextAlign.End -> "end"
            TextAlign.Unspecified -> "unspecified"
            else -> error("Unsupported TextAlign value '$value'. Use either 'left', 'right', 'center', 'justify', 'start', 'end', or 'unspecified'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun TextAlign.Companion.serializer(): KSerializer<TextAlign> =
    TextAlignAsStringSerializer
