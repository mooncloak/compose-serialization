package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.TextUnit
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal class ParagraphStyleSurrogate internal constructor(
    @SerialName(value = "text_align") @Serializable(with = TextAlignAsStringSerializer::class) val textAlign: TextAlign = TextAlign.Unspecified,
    @SerialName(value = "text_direction") @Serializable(with = TextDirectionAsStringSerializer::class) val textDirection: TextDirection = TextDirection.Unspecified,
    @SerialName(value = "line_height") @Serializable(with = TextUnitSerializer::class) val lineHeight: TextUnit = TextUnit.Unspecified,
    @SerialName(value = "text_indent") @Serializable(with = TextIndentSerializer::class) val textIndent: TextIndent? = null,
    @SerialName(value = "line_height_style") @Serializable(with = LineHeightStyleSerializer::class) val lineHeightStyle: LineHeightStyle? = null,
    @SerialName(value = "line_break") @Serializable(with = LineBreakAsStringSerializer::class) val lineBreak: LineBreak = LineBreak.Unspecified,
    @SerialName(value = "hyphens") @Serializable(with = HyphensAsStringSerializer::class) val hyphens: Hyphens = Hyphens.Unspecified,
    @SerialName(value = "text_motion") @Serializable(with = TextMotionAsStringSerializer::class) val textMotion: TextMotion? = null
)

public data object ParagraphStyleSerializer : KSerializer<ParagraphStyle> {

    override val descriptor: SerialDescriptor
        get() = ParagraphStyleSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): ParagraphStyle {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = ParagraphStyleSurrogate.serializer()
        )

        return ParagraphStyle(
            textAlign = surrogate.textAlign,
            textDirection = surrogate.textDirection,
            lineHeight = surrogate.lineHeight,
            textIndent = surrogate.textIndent,
            lineHeightStyle = surrogate.lineHeightStyle,
            lineBreak = surrogate.lineBreak,
            hyphens = surrogate.hyphens,
            textMotion = surrogate.textMotion
        )
    }

    override fun serialize(encoder: Encoder, value: ParagraphStyle) {
        val surrogate = ParagraphStyleSurrogate(
            textAlign = value.textAlign,
            textDirection = value.textDirection,
            lineHeight = value.lineHeight,
            textIndent = value.textIndent,
            lineHeightStyle = value.lineHeightStyle,
            lineBreak = value.lineBreak,
            hyphens = value.hyphens,
            textMotion = value.textMotion
        )

        encoder.encodeSerializableValue(
            serializer = ParagraphStyleSurrogate.serializer(),
            value = surrogate
        )
    }
}
