package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class ShadowSurrogate internal constructor(
    @SerialName(value = "color") @Serializable(with = ColorAsULongSerializer::class) val color: Color,
    @SerialName(value = "offset") @Serializable(with = OffsetSerializer::class) val offset: Offset,
    @SerialName(value = "blur_radius") val blurRadius: Float
)

public data object ShadowSerializer : KSerializer<Shadow> {

    override val descriptor: SerialDescriptor
        get() = ShadowSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): Shadow {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = ShadowSurrogate.serializer()
        )

        return Shadow(
            color = surrogate.color,
            offset = surrogate.offset,
            blurRadius = surrogate.blurRadius
        )
    }

    override fun serialize(encoder: Encoder, value: Shadow) {
        val surrogate = ShadowSurrogate(
            color = value.color,
            offset = value.offset,
            blurRadius = value.blurRadius
        )

        encoder.encodeSerializableValue(
            serializer = ShadowSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun Shadow.Companion.serializer(): KSerializer<Shadow> =
    ShadowSerializer
