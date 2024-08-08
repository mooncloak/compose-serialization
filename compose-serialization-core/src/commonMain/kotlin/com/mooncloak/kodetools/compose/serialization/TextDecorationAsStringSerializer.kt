package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.TextDecoration
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object TextDecorationAsStringSerializer : KSerializer<TextDecoration> {

    private val delegate = ListSerializer(String.serializer())

    override val descriptor: SerialDescriptor
        get() = delegate.descriptor

    override fun deserialize(decoder: Decoder): TextDecoration {
        val values = decoder.decodeSerializableValue(
            deserializer = delegate
        )

        return when {
            values.isEmpty() -> TextDecoration.None

            values.size == 1 -> values.first().toTextDecoration()

            else -> {
                val decorations = values.map { it.toTextDecoration() }

                var result = decorations.first()

                for (i in 1 until decorations.size) {
                    result += decorations[i]
                }

                result
            }
        }
    }

    override fun serialize(encoder: Encoder, value: TextDecoration) {
        val list = mutableListOf<String>()

        if (value == TextDecoration.None || value.contains(TextDecoration.None)) {
            list.add("none")
        }

        if (value == TextDecoration.Underline || value.contains(TextDecoration.Underline)) {
            list.add("underline")
        }

        if (value == TextDecoration.LineThrough || value.contains(TextDecoration.LineThrough)) {
            list.add("line_through")
        }

        encoder.encodeSerializableValue(
            serializer = delegate,
            value = list
        )
    }

    private fun String.toTextDecoration(): TextDecoration =
        when (this.lowercase()) {
            "none" -> TextDecoration.None
            "underline" -> TextDecoration.Underline
            "line_through" -> TextDecoration.LineThrough
            else -> error("Unsupported TextDecoration value '$this'. Use 'none', 'underline', or 'line_through'.")
        }
}

public fun TextDecoration.Companion.serializer(): KSerializer<TextDecoration> =
    TextDecorationAsStringSerializer
