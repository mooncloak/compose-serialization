package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.unit.IntOffset
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class IntOffsetSurrogate internal constructor(
    @SerialName(value = "x") val x: Int,
    @SerialName(value = "y") val y: Int
)

public data object IntOffsetSerializer : KSerializer<IntOffset> {

    override val descriptor: SerialDescriptor
        get() = IntOffsetSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): IntOffset {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = IntOffsetSurrogate.serializer()
        )

        return IntOffset(
            x = surrogate.x,
            y = surrogate.y
        )
    }

    override fun serialize(encoder: Encoder, value: IntOffset) {
        val surrogate = IntOffsetSurrogate(
            x = value.x,
            y = value.y
        )

        encoder.encodeSerializableValue(
            serializer = IntOffsetSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun IntOffset.Companion.serializer(): KSerializer<IntOffset> =
    IntOffsetSerializer
