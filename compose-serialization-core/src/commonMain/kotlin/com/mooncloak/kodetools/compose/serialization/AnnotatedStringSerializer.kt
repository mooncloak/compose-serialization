package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal sealed interface TextAnnotation {

    val value: String?
}

@Serializable
@SerialName(value = "string")
internal class StringAnnotation internal constructor(
    override val value: String
) : TextAnnotation

@Serializable
@SerialName(value = "uri")
internal class UriAnnotation internal constructor(
    override val value: String
) : TextAnnotation

@Serializable
@SerialName(value = "verbatim_tts")
internal class VerbatimTtsAnnotation internal constructor(
    override val value: String
) : TextAnnotation

@Serializable
internal class AnnotatedStringSurrogate @OptIn(ExperimentalTextApi::class) internal constructor(
    @SerialName(value = "text") val text: String,
    @SerialName(value = "span_styles") val spanStyles: List<@Serializable(with = AnnotatedStringRangeSerializer::class) AnnotatedString.Range<@Serializable(
        with = SpanStyleSerializer::class
    ) SpanStyle>>? = null,
    @SerialName(value = "paragraph_styles") val paragraphStyles: List<@Serializable(with = AnnotatedStringRangeSerializer::class) AnnotatedString.Range<@Serializable(
        with = ParagraphStyleSerializer::class
    ) ParagraphStyle>>? = null,
    @SerialName(value = "annotations") val annotations: List<@Serializable(with = AnnotatedStringRangeSerializer::class) AnnotatedString.Range<out TextAnnotation>>? = null
)

@OptIn(ExperimentalTextApi::class)
public data object AnnotatedStringSerializer : KSerializer<AnnotatedString> {

    override val descriptor: SerialDescriptor
        get() = AnnotatedStringSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): AnnotatedString {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = AnnotatedStringSurrogate.serializer()
        )
        val builder = AnnotatedString.Builder(
            text = AnnotatedString(
                text = surrogate.text,
                spanStyles = surrogate.spanStyles ?: emptyList(),
                paragraphStyles = surrogate.paragraphStyles ?: emptyList()
            )
        )

        surrogate.annotations?.forEach { range ->
            when (val item = range.item) {
                is StringAnnotation -> builder.addStringAnnotation(
                    tag = range.tag,
                    annotation = item.value,
                    start = range.start,
                    end = range.end
                )

                is UriAnnotation -> builder.addUrlAnnotation(
                    urlAnnotation = UrlAnnotation(url = item.value),
                    start = range.start,
                    end = range.end
                )

                is VerbatimTtsAnnotation -> builder.addTtsAnnotation(
                    ttsAnnotation = VerbatimTtsAnnotation(verbatim = item.value),
                    start = range.start,
                    end = range.end
                )
            }
        }

        return builder.toAnnotatedString()
    }

    override fun serialize(encoder: Encoder, value: AnnotatedString) {
        val stringAnnotations = value.getStringAnnotations(
            start = 0,
            end = value.length
        ).map { range ->
            AnnotatedString.Range(
                item = StringAnnotation(value = range.item),
                start = range.start,
                end = range.end,
                tag = range.tag
            )
        }
        val uriAnnotations = value.getUrlAnnotations(
            start = 0,
            end = value.length
        ).map { range ->
            AnnotatedString.Range(
                item = UriAnnotation(value = range.item.url),
                start = range.start,
                end = range.end,
                tag = range.tag
            )
        }
        val ttsAnnotations = value.getTtsAnnotations(
            start = 0,
            end = value.length
        ).map { range ->
            AnnotatedString.Range(
                item = VerbatimTtsAnnotation(
                    value = (range.item as? androidx.compose.ui.text.VerbatimTtsAnnotation)?.verbatim
                        ?: range.item.toString()
                ),
                start = range.start,
                end = range.end,
                tag = range.tag
            )
        }

        val surrogate = AnnotatedStringSurrogate(
            text = value.text,
            spanStyles = value.spanStyles.takeIf { it.isNotEmpty() },
            paragraphStyles = value.paragraphStyles.takeIf { it.isNotEmpty() },
            annotations = stringAnnotations + uriAnnotations + ttsAnnotations
        )

        encoder.encodeSerializableValue(
            serializer = AnnotatedStringSurrogate.serializer(),
            value = surrogate
        )
    }
}
