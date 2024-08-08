package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.font.FontStyle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object FontStyleAsStringSerializer : KSerializer<FontStyle> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): FontStyle {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "normal" -> FontStyle.Normal
            "italic" -> FontStyle.Italic
            else -> error("Unsupported FontStyle value '$value'. Use 'normal' or 'italic'.")
        }
    }

    override fun serialize(encoder: Encoder, value: FontStyle) {
        val stringValue = when (value) {
            FontStyle.Normal -> "normal"
            FontStyle.Italic -> "italic"
            else -> error("Unsupported FontStyle value '$value'. Use 'normal' or 'italic'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun FontStyle.Companion.serializer(): KSerializer<FontStyle> =
    FontStyleAsStringSerializer
