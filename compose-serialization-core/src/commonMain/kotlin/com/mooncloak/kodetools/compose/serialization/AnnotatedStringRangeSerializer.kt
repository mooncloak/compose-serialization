package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.AnnotatedString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class AnnotatedStringRangeSurrogate<T> internal constructor(
    @SerialName(value = "item") val item: T,
    @SerialName(value = "start") val start: Int,
    @SerialName(value = "end") val end: Int,
    @SerialName(value = "tag") val tag: String
)

public class AnnotatedStringRangeSerializer<T> public constructor(
    typeSerializer: KSerializer<T>
) : KSerializer<AnnotatedString.Range<T>> {

    private val surrogateSerializer = AnnotatedStringRangeSurrogate.serializer(typeSerializer)

    override val descriptor: SerialDescriptor
        get() = surrogateSerializer.descriptor

    override fun deserialize(decoder: Decoder): AnnotatedString.Range<T> {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = surrogateSerializer
        )

        return AnnotatedString.Range(
            item = surrogate.item,
            start = surrogate.start,
            end = surrogate.end,
            tag = surrogate.tag
        )
    }

    override fun serialize(encoder: Encoder, value: AnnotatedString.Range<T>) {
        val surrogate = AnnotatedStringRangeSurrogate(
            item = value.item,
            start = value.start,
            end = value.end,
            tag = value.tag
        )

        encoder.encodeSerializableValue(
            serializer = surrogateSerializer,
            value = surrogate
        )
    }
}
