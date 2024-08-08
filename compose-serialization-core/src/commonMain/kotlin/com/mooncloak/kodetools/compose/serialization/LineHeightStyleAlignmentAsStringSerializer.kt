package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.LineHeightStyle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object LineHeightStyleAlignmentAsStringSerializer :
    KSerializer<LineHeightStyle.Alignment> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): LineHeightStyle.Alignment {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "top" -> LineHeightStyle.Alignment.Top
            "center" -> LineHeightStyle.Alignment.Center
            "proportional" -> LineHeightStyle.Alignment.Proportional
            "bottom" -> LineHeightStyle.Alignment.Bottom
            else -> error("Unsupported LineHeightStyle.Alignment value '$value'. Use 'top', 'center', 'proportional', or 'bottom'.")
        }
    }

    override fun serialize(encoder: Encoder, value: LineHeightStyle.Alignment) {
        val stringValue = when (value) {
            LineHeightStyle.Alignment.Top -> "top"
            LineHeightStyle.Alignment.Center -> "center"
            LineHeightStyle.Alignment.Proportional -> "proportional"
            LineHeightStyle.Alignment.Bottom -> "bottom"
            else -> error("Unsupported LineHeightStyle.Alignment value '$value'. Use 'top', 'center', 'proportional', or 'bottom'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun LineHeightStyle.Alignment.Companion.serializer(): KSerializer<LineHeightStyle.Alignment> =
    LineHeightStyleAlignmentAsStringSerializer
