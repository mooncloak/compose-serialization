package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object DeviceIndependentPixelSerializer : KSerializer<Dp> {

    override val descriptor: SerialDescriptor
        get() = Float.serializer().descriptor

    override fun deserialize(decoder: Decoder): Dp {
        val value = decoder.decodeFloat()

        return Dp(value = value)
    }

    override fun serialize(encoder: Encoder, value: Dp) {
        encoder.encodeFloat(value.value)
    }
}

public fun Dp.Companion.serializer(): KSerializer<Dp> =
    DeviceIndependentPixelSerializer
