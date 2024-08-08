package com.mooncloak.kodetools.compose.serialization

/* FIXME
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextDecorationLineStyle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@ExperimentalTextApi
public data object TextDecorationLineStyleAsStringSerializer :
    KSerializer<TextDecorationLineStyle> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): TextDecorationLineStyle {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "solid" -> TextDecorationLineStyle.Solid
            "double" -> TextDecorationLineStyle.Double
            "dotted" -> TextDecorationLineStyle.Dotted
            "dashed" -> TextDecorationLineStyle.Dashed
            "wavy" -> TextDecorationLineStyle.Wavy
            else -> error("Unsupported TextDecorationLineStyle value '$value'. Use 'solid', 'double', 'dotted', 'dashed', or 'wavy'.")
        }
    }

    override fun serialize(encoder: Encoder, value: TextDecorationLineStyle) {
        val stringValue = when (value) {
            TextDecorationLineStyle.Solid -> "solid"
            TextDecorationLineStyle.Double -> "double"
            TextDecorationLineStyle.Dotted -> "dotted"
            TextDecorationLineStyle.Dashed -> "dashed"
            TextDecorationLineStyle.Wavy -> "wavy"
            else -> error("Unsupported TextDecorationLineStyle value '$value'. Use 'solid', 'double', 'dotted', 'dashed', or 'wavy'.")
        }

        encoder.encodeString(stringValue)
    }
}

@ExperimentalTextApi
public fun TextDecorationLineStyle.Companion.serializer(): KSerializer<TextDecorationLineStyle> =
    TextDecorationLineStyleAsStringSerializer
*/