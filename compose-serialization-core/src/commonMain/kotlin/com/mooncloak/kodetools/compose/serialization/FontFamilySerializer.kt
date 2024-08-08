package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.font.FontFamily
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal sealed interface FontFamilySurrogate {

    @Serializable
    @SerialName(value = "system_default")
    data object SystemDefault : FontFamilySurrogate

    @Serializable
    @SerialName(value = "sans_serif")
    data object SansSerif : FontFamilySurrogate

    @Serializable
    @SerialName(value = "serif")
    data object Serif : FontFamilySurrogate

    @Serializable
    @SerialName(value = "monospace")
    data object Monospace : FontFamilySurrogate

    @Serializable
    @SerialName(value = "cursive")
    data object Cursive : FontFamilySurrogate
}

public data object FontFamilySerializer : KSerializer<FontFamily> {

    override val descriptor: SerialDescriptor
        get() = FontFamilySurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): FontFamily {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = FontFamilySurrogate.serializer()
        )

        return when (surrogate) {
            is FontFamilySurrogate.SystemDefault -> FontFamily.Default
            is FontFamilySurrogate.SansSerif -> FontFamily.SansSerif
            is FontFamilySurrogate.Serif -> FontFamily.Serif
            is FontFamilySurrogate.Monospace -> FontFamily.Monospace
            is FontFamilySurrogate.Cursive -> FontFamily.Cursive
        }
    }

    override fun serialize(encoder: Encoder, value: FontFamily) {
        val surrogate = when (value) {
            FontFamily.Default -> FontFamilySurrogate.SystemDefault
            FontFamily.SansSerif -> FontFamilySurrogate.SansSerif
            FontFamily.Serif -> FontFamilySurrogate.Serif
            FontFamily.Monospace -> FontFamilySurrogate.Monospace
            FontFamily.Cursive -> FontFamilySurrogate.Cursive
            else -> error("Only the system default and generic FontFamily types are supported for serialization.")
        }

        encoder.encodeSerializableValue(
            serializer = FontFamilySurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun FontFamily.Companion.serializer(): KSerializer<FontFamily> =
    FontFamilySerializer
