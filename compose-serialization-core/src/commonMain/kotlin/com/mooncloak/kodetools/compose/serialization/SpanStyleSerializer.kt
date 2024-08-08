package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.unit.TextUnit
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
@ExperimentalTextApi
internal class SpanStyleSurrogate internal constructor(
    @SerialName(value = "color") @Serializable(with = ColorAsULongSerializer::class) val color: Color,
    @SerialName(value = "font_size") @Serializable(with = TextUnitSerializer::class) val fontSize: TextUnit,
    @SerialName(value = "font_weight") @Serializable(with = FontWeightSerializer::class) val fontWeight: FontWeight?,
    @SerialName(value = "font_style") @Serializable(with = FontStyleAsStringSerializer::class) val fontStyle: FontStyle?,
    @SerialName(value = "font_synthesis") @Serializable(with = FontSynthesisAsStringSerializer::class) val fontSynthesis: FontSynthesis?,
    @SerialName(value = "font_family") @Serializable(with = FontFamilySerializer::class) val fontFamily: FontFamily?,
    @SerialName(value = "font_feature_settings") val fontFeatureSettings: String?,
    @SerialName(value = "letter_spacing") @Serializable(with = TextUnitSerializer::class) val letterSpacing: TextUnit,
    @SerialName(value = "baseline_shift") @Serializable(with = BaselineShiftAsStringSerializer::class) val baselineShift: BaselineShift?,
    @SerialName(value = "text_geometric_transform") @Serializable(with = TextGeometricTransformSerializer::class) val textGeometricTransform: TextGeometricTransform?,
    @SerialName(value = "background") @Serializable(with = ColorAsULongSerializer::class) val background: Color,
    @SerialName(value = "text_decoration") @Serializable(with = TextDecorationAsStringSerializer::class) val textDecoration: TextDecoration?,
    @SerialName(value = "shadow") @Serializable(with = ShadowSerializer::class) val shadow: Shadow?,
    // FIXME: @SerialName(value = "text_decoration_line_style") @Serializable(with = TextDecorationLineStyleAsStringSerializer::class) val textDecorationLineStyle: TextDecorationLineStyle?,
    @SerialName(value = "draw_style") @Serializable(with = DrawStyleSerializer::class) val drawStyle: DrawStyle? = null
)

@ExperimentalTextApi
public data object SpanStyleSerializer : KSerializer<SpanStyle> {

    override val descriptor: SerialDescriptor
        get() = SpanStyleSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): SpanStyle {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = SpanStyleSurrogate.serializer()
        )

        return SpanStyle(
            color = surrogate.color,
            fontSize = surrogate.fontSize,
            fontWeight = surrogate.fontWeight,
            fontStyle = surrogate.fontStyle,
            fontSynthesis = surrogate.fontSynthesis,
            fontFamily = surrogate.fontFamily,
            fontFeatureSettings = surrogate.fontFeatureSettings,
            letterSpacing = surrogate.letterSpacing,
            baselineShift = surrogate.baselineShift,
            textGeometricTransform = surrogate.textGeometricTransform,
            background = surrogate.background,
            textDecoration = surrogate.textDecoration,
            shadow = surrogate.shadow,
            //platformStyle = surrogate.textDecorationLineStyle?.let { PlatformSpanStyle(it) },
            drawStyle = surrogate.drawStyle
        )
    }

    override fun serialize(encoder: Encoder, value: SpanStyle) {
        val surrogate = SpanStyleSurrogate(
            color = value.color,
            fontSize = value.fontSize,
            fontWeight = value.fontWeight,
            fontStyle = value.fontStyle,
            fontSynthesis = value.fontSynthesis,
            fontFamily = value.fontFamily,
            fontFeatureSettings = value.fontFeatureSettings,
            letterSpacing = value.letterSpacing,
            baselineShift = value.baselineShift,
            textGeometricTransform = value.textGeometricTransform,
            background = value.background,
            textDecoration = value.textDecoration,
            shadow = value.shadow,
            //textDecorationLineStyle = value.platformStyle?.textDecorationLineStyle,
            drawStyle = value.drawStyle
        )

        encoder.encodeSerializableValue(
            serializer = SpanStyleSurrogate.serializer(),
            value = surrogate
        )
    }
}
