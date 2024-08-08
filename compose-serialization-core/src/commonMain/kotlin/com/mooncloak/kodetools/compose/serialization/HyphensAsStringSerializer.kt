package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.Hyphens
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object HyphensAsStringSerializer : KSerializer<Hyphens> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): Hyphens {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "none" -> Hyphens.None
            "auto" -> Hyphens.Auto
            "unspecified" -> Hyphens.Unspecified
            else -> error("Unsupported Hyphens value '$value'. Use 'none', 'auto', or 'unspecified'.")
        }
    }

    override fun serialize(encoder: Encoder, value: Hyphens) {
        val stringValue = when (value) {
            Hyphens.None -> "none"
            Hyphens.Auto -> "auto"
            Hyphens.Unspecified -> "unspecified"
            else -> error("Unsupported Hyphens value '$value'. Use 'none', 'auto', or 'unspecified'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun Hyphens.Companion.serializer(): KSerializer<Hyphens> =
    HyphensAsStringSerializer
