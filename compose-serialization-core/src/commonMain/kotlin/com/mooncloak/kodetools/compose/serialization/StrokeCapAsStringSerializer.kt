package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.StrokeCap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object StrokeCapAsStringSerializer : KSerializer<StrokeCap> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): StrokeCap {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "butt" -> StrokeCap.Butt
            "round" -> StrokeCap.Round
            "square" -> StrokeCap.Square
            else -> error("Unsupported StrokeCap value '$value'. Use 'butt', 'round', or 'square'.")
        }
    }

    override fun serialize(encoder: Encoder, value: StrokeCap) {
        val stringValue = when (value) {
            StrokeCap.Butt -> "butt"
            StrokeCap.Round -> "round"
            StrokeCap.Square -> "square"
            else -> error("Unsupported StrokeCap value '$value'. Use 'butt', 'round', or 'square'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun StrokeCap.Companion.serializer(): KSerializer<StrokeCap> =
    StrokeCapAsStringSerializer
