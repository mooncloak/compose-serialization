package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.TextDirection
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object TextDirectionAsStringSerializer : KSerializer<TextDirection> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextDirection {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "ltr" -> TextDirection.Ltr
            "rtl" -> TextDirection.Rtl
            "content" -> TextDirection.Content
            "content_or_ltr" -> TextDirection.ContentOrLtr
            "content_or_rtl" -> TextDirection.ContentOrRtl
            "unspecified" -> TextDirection.Unspecified
            else -> error("Unsupported TextDirection value '$value'. Use either 'lrt', 'rtl', 'content', 'content_or_ltr', 'content_or_rtl', or 'unspecified'.")
        }
    }

    override fun serialize(encoder: Encoder, value: TextDirection) {
        val stringValue = when (value) {
            TextDirection.Ltr -> "ltr"
            TextDirection.Rtl -> "rtl"
            TextDirection.Content -> "content"
            TextDirection.ContentOrLtr -> "content_or_ltr"
            TextDirection.ContentOrRtl -> "content_or_rtl"
            TextDirection.Unspecified -> "unspecified"
            else -> error("Unsupported TextDirection value '$value'. Use either 'lrt', 'rtl', 'content', 'content_or_ltr', 'content_or_rtl', or 'unspecified'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun TextDirection.Companion.serializer(): KSerializer<TextDirection> =
    TextDirectionAsStringSerializer
