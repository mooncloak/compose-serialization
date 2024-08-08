package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.StrokeJoin
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object StrokeJoinAsStringSerializer : KSerializer<StrokeJoin> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): StrokeJoin {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "miter" -> StrokeJoin.Miter
            "round" -> StrokeJoin.Round
            "bevel" -> StrokeJoin.Bevel
            else -> error("Unsupported StrokeJoin value '$value'. Use 'miter', 'round', or 'bevel'.")
        }
    }

    override fun serialize(encoder: Encoder, value: StrokeJoin) {
        val stringValue = when (value) {
            StrokeJoin.Miter -> "miter"
            StrokeJoin.Round -> "round"
            StrokeJoin.Bevel -> "bevel"
            else -> error("Unsupported StrokeJoin value '$value'. Use 'miter', 'round', or 'bevel'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun StrokeJoin.Companion.serializer(): KSerializer<StrokeJoin> =
    StrokeJoinAsStringSerializer
