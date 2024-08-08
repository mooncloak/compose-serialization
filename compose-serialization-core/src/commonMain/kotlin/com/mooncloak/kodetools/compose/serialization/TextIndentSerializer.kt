package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class TextIndentSurrogate internal constructor(
    @SerialName(value = "first_line") @Serializable(with = TextUnitSerializer::class) val firstLine: TextUnit,
    @SerialName(value = "rest_line") @Serializable(with = TextUnitSerializer::class) val restLine: TextUnit
)

public data object TextIndentSerializer : KSerializer<TextIndent> {

    override val descriptor: SerialDescriptor
        get() = TextIndentSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextIndent {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = TextIndentSurrogate.serializer()
        )

        return TextIndent(
            firstLine = surrogate.firstLine,
            restLine = surrogate.restLine
        )
    }

    override fun serialize(encoder: Encoder, value: TextIndent) {
        val surrogate = TextIndentSurrogate(
            firstLine = value.firstLine,
            restLine = value.restLine
        )

        encoder.encodeSerializableValue(
            serializer = TextIndentSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun TextIndent.Companion.serializer(): KSerializer<TextIndent> =
    TextIndentSerializer
