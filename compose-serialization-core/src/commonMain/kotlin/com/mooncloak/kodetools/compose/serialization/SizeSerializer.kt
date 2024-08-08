package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.geometry.Size
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class SizeSurrogate internal constructor(
    @SerialName(value = "width") val width: Float,
    @SerialName(value = "height") val height: Float
)

public data object SizeSerializer : KSerializer<Size> {

    override val descriptor: SerialDescriptor
        get() = SizeSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): Size {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = SizeSurrogate.serializer()
        )

        return Size(
            width = surrogate.width,
            height = surrogate.height
        )
    }

    override fun serialize(encoder: Encoder, value: Size) {
        val surrogate = SizeSurrogate(
            width = value.width,
            height = value.height
        )

        encoder.encodeSerializableValue(
            serializer = SizeSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun Size.Companion.serializer(): KSerializer<Size> =
    SizeSerializer
