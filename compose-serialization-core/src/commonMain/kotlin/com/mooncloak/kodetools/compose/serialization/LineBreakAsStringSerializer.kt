package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.LineBreak
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object LineBreakAsStringSerializer : KSerializer<LineBreak> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): LineBreak {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "simple" -> LineBreak.Simple
            "heading" -> LineBreak.Heading
            "paragraph" -> LineBreak.Paragraph
            "unspecified" -> LineBreak.Unspecified
            else -> error("Unsupported LineBreak value '$value'. Use 'simple', 'heading', 'paragraph', or 'unspecified'.")
        }
    }

    override fun serialize(encoder: Encoder, value: LineBreak) {
        val stringValue = when (value) {
            LineBreak.Simple -> "simple"
            LineBreak.Heading -> "heading"
            LineBreak.Paragraph -> "paragraph"
            LineBreak.Unspecified -> "unspecified"
            else -> error("Unsupported LineBreak value '$value'. Use 'simple', 'heading', 'paragraph', or 'unspecified'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun LineBreak.Companion.serializer(): KSerializer<LineBreak> =
    LineBreakAsStringSerializer
