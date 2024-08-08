package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.font.FontWeight
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object FontWeightSerializer : KSerializer<FontWeight> {

    override val descriptor: SerialDescriptor
        get() = Int.serializer().descriptor

    override fun deserialize(decoder: Decoder): FontWeight {
        val value = decoder.decodeInt()

        return FontWeight(value)
    }

    override fun serialize(encoder: Encoder, value: FontWeight) {
        encoder.encodeInt(value.weight)
    }
}

public fun FontWeight.Companion.serializer(): KSerializer<FontWeight> =
    FontWeightSerializer
