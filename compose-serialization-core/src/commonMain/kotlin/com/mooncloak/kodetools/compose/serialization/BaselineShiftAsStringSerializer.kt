package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.text.style.BaselineShift
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public data object BaselineShiftAsStringSerializer : KSerializer<BaselineShift> {

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): BaselineShift {
        val value = decoder.decodeString()

        return when (value.lowercase()) {
            "superscript" -> BaselineShift.Superscript
            "subscript" -> BaselineShift.Subscript
            "none" -> BaselineShift.None
            else -> error("Unsupported BaselineShift value '$value'. Use 'superscript', 'subscript', or 'none'.")
        }
    }

    override fun serialize(encoder: Encoder, value: BaselineShift) {
        val stringValue = when (value) {
            BaselineShift.Superscript -> "superscript"
            BaselineShift.Subscript -> "subscript"
            BaselineShift.None -> "none"
            else -> error("Unsupported BaselineShift value '$value'. Use 'superscript', 'subscript', or 'none'.")
        }

        encoder.encodeString(stringValue)
    }
}

public fun BaselineShift.Companion.serializer(): KSerializer<BaselineShift> =
    BaselineShiftAsStringSerializer
