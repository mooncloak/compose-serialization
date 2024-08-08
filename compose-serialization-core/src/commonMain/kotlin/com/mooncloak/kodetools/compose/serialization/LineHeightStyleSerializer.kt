package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.LineHeightStyle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class LineHeightStyleSurrogate internal constructor(
    @SerialName(value = "alignment") @Serializable(with = LineHeightStyleAlignmentAsStringSerializer::class) val alignment: LineHeightStyle.Alignment,
    @SerialName(value = "trim") @Serializable(with = LineHeightStyleTrimAsStringSerializer::class) val trim: LineHeightStyle.Trim
)

public data object LineHeightStyleSerializer : KSerializer<LineHeightStyle> {

    override val descriptor: SerialDescriptor = LineHeightStyleSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): LineHeightStyle {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = LineHeightStyleSurrogate.serializer()
        )

        return LineHeightStyle(
            alignment = surrogate.alignment,
            trim = surrogate.trim
        )
    }

    override fun serialize(encoder: Encoder, value: LineHeightStyle) {
        val surrogate = LineHeightStyleSurrogate(
            alignment = value.alignment,
            trim = value.trim
        )

        encoder.encodeSerializableValue(
            serializer = LineHeightStyleSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun LineHeightStyle.Companion.serializer(): KSerializer<LineHeightStyle> =
    LineHeightStyleSerializer
