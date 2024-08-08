package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class TextUnitSurrogate internal constructor(
    @SerialName(value = "value") val value: Float,
    @SerialName(value = "type") val type: String
)

public data object TextUnitSerializer : KSerializer<TextUnit> {

    override val descriptor: SerialDescriptor
        get() = TextUnitSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextUnit {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = TextUnitSurrogate.serializer()
        )

        val type = when (surrogate.type.lowercase()) {
            "sp" -> TextUnitType.Sp
            "em" -> TextUnitType.Em
            "unspecified" -> TextUnitType.Unspecified
            else -> error("Unsupported TextUnitType value '${surrogate.type}'. Use 'sp', 'em', or 'unspecified'.")
        }

        return TextUnit(
            value = surrogate.value,
            type = type
        )
    }

    override fun serialize(encoder: Encoder, value: TextUnit) {
        val surrogate = TextUnitSurrogate(
            value = value.value,
            type = value.type.toString().lowercase()
        )

        encoder.encodeSerializableValue(
            serializer = TextUnitSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun TextUnit.Companion.serializer(): KSerializer<TextUnit> =
    TextUnitSerializer
