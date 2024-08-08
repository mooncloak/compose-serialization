package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.font.FontSynthesis
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object FontSynthesisAsStringSerializer : KSerializer<FontSynthesis> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): FontSynthesis {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "none" -> FontSynthesis.None
            "all" -> FontSynthesis.All
            "weight" -> FontSynthesis.Weight
            "style" -> FontSynthesis.Style
            else -> error("Unsupported FontSynthesis value '$value'. Use 'none', 'all', 'weight', or 'style'.")
        }
    }

    override fun serialize(encoder: Encoder, value: FontSynthesis) {
        val stringValue = when (value) {
            FontSynthesis.None -> "none"
            FontSynthesis.All -> "all"
            FontSynthesis.Weight -> "weight"
            FontSynthesis.Style -> "style"
            else -> error("Unsupported FontSynthesis value '$value'. Use 'none', 'all', 'weight', or 'style'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun FontSynthesis.Companion.serializer(): KSerializer<FontSynthesis> =
    FontSynthesisAsStringSerializer
