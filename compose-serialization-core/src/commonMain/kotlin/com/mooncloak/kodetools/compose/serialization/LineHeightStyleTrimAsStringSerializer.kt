package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.LineHeightStyle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object LineHeightStyleTrimAsStringSerializer : KSerializer<LineHeightStyle.Trim> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): LineHeightStyle.Trim {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "first_line_top" -> LineHeightStyle.Trim.FirstLineTop
            "last_line_bottom" -> LineHeightStyle.Trim.LastLineBottom
            "both" -> LineHeightStyle.Trim.Both
            "none" -> LineHeightStyle.Trim.None
            else -> error("Unsupported LineHeightStyle.Trim value '$value'. Use 'first_line_top', 'last_line_bottom', 'both', or 'none'.")
        }
    }

    override fun serialize(encoder: Encoder, value: LineHeightStyle.Trim) {
        val stringValue = when (value) {
            LineHeightStyle.Trim.FirstLineTop -> "first_line_top"
            LineHeightStyle.Trim.LastLineBottom -> "last_line_bottom"
            LineHeightStyle.Trim.Both -> "both"
            LineHeightStyle.Trim.None -> "none"
            else -> error("Unsupported LineHeightStyle.Trim value '$value'. Use 'first_line_top', 'last_line_bottom', 'both', or 'none'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun LineHeightStyle.Trim.Companion.serializer(): KSerializer<LineHeightStyle.Trim> =
    LineHeightStyleTrimAsStringSerializer
