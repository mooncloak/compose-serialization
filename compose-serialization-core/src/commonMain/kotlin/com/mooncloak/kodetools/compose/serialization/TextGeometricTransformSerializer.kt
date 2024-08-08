package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.TextGeometricTransform
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class TextGeometricTransformSurrogate internal constructor(
    @SerialName(value = "scale_x") val scaleX: Float,
    @SerialName(value = "skew_x") val skewX: Float
)

public data object TextGeometricTransformSerializer : KSerializer<TextGeometricTransform> {

    override val descriptor: SerialDescriptor
        get() = TextGeometricTransformSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextGeometricTransform {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = TextGeometricTransformSurrogate.serializer()
        )

        return TextGeometricTransform(
            scaleX = surrogate.scaleX,
            skewX = surrogate.skewX
        )
    }

    override fun serialize(encoder: Encoder, value: TextGeometricTransform) {
        val surrogate = TextGeometricTransformSurrogate(
            scaleX = value.scaleX,
            skewX = value.skewX
        )

        encoder.encodeSerializableValue(
            serializer = TextGeometricTransformSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun TextGeometricTransform.Companion.serializer(): KSerializer<TextGeometricTransform> =
    TextGeometricTransformSerializer
