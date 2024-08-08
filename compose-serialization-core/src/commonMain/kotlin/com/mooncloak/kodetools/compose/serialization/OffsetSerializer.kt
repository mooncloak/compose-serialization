package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class OffsetSurrogate internal constructor(
    @SerialName(value = "x") val x: Float,
    @SerialName(value = "y") val y: Float
)

public data object OffsetSerializer : KSerializer<Offset> {

    override val descriptor: SerialDescriptor
        get() = OffsetSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): Offset {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = OffsetSurrogate.serializer()
        )

        return Offset(
            x = surrogate.x,
            y = surrogate.y
        )
    }

    override fun serialize(encoder: Encoder, value: Offset) {
        val surrogate = OffsetSurrogate(
            x = value.x,
            y = value.y
        )

        encoder.encodeSerializableValue(
            serializer = OffsetSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun Offset.Companion.serializer(): KSerializer<Offset> =
    OffsetSerializer
