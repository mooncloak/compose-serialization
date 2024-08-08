package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.unit.IntSize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class IntSizeSurrogate internal constructor(
    @SerialName(value = "width") val width: Int,
    @SerialName(value = "height") val height: Int
)

public data object IntSizeSerializer : KSerializer<IntSize> {

    override val descriptor: SerialDescriptor
        get() = SizeSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): IntSize {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = IntSizeSurrogate.serializer()
        )

        return IntSize(
            width = surrogate.width,
            height = surrogate.height
        )
    }

    override fun serialize(encoder: Encoder, value: IntSize) {
        val surrogate = IntSizeSurrogate(
            width = value.width,
            height = value.height
        )

        encoder.encodeSerializableValue(
            serializer = IntSizeSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun IntSize.Companion.serializer(): KSerializer<IntSize> =
    IntSizeSerializer
