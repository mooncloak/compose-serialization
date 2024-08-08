package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.TileMode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object TileModeAsStringSerializer : KSerializer<TileMode> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): TileMode {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "clamp" -> TileMode.Clamp
            "repeated" -> TileMode.Repeated
            "mirror" -> TileMode.Mirror
            "decal" -> TileMode.Decal
            else -> error("Unsupported TileMode value '$value'. Use 'clamp', 'repeated', 'mirror', or 'decal'.")
        }
    }

    override fun serialize(encoder: Encoder, value: TileMode) {
        val stringValue = when (value) {
            TileMode.Clamp -> "clamp"
            TileMode.Repeated -> "repeated"
            TileMode.Mirror -> "mirror"
            TileMode.Decal -> "decal"
            else -> error("Unsupported TileMode value '$value'. Use 'clamp', 'repeated', 'mirror', or 'decal'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun TileMode.Companion.serializer(): KSerializer<TileMode> =
    TileModeAsStringSerializer
