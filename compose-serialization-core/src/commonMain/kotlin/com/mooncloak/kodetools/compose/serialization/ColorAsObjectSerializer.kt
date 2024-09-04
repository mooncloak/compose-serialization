package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline

@Serializable
internal sealed interface ColorObjectSurrogate {

    @Serializable
    @SerialName(value = "argb")
    @JvmInline
    value class ArgbColor internal constructor(
        internal val value: Int
    ) : ColorObjectSurrogate

    @Serializable
    @SerialName(value = "rgba")
    class RgbaColor internal constructor(
        @SerialName(value = "red") internal val red: Float,
        @SerialName(value = "green") internal val green: Float,
        @SerialName(value = "blue") internal val blue: Float,
        @SerialName(value = "alpha") internal val alpha: Float
    ) : ColorObjectSurrogate

    @Serializable
    @SerialName(value = "ulong")
    @JvmInline
    value class ULongColor internal constructor(
        internal val value: ULong
    )
}

public data object ColorAsArgbObjectSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor
        get() = ColorObjectSurrogate.ArgbColor.serializer().descriptor

    override fun deserialize(decoder: Decoder): Color {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = ColorObjectSurrogate.ArgbColor.serializer()
        )

        return Color(surrogate.value)
    }

    override fun serialize(encoder: Encoder, value: Color) {
        val surrogate = ColorObjectSurrogate.ArgbColor(
            value.toArgb()
        )

        encoder.encodeSerializableValue(
            serializer = ColorObjectSurrogate.ArgbColor.serializer(),
            value = surrogate
        )
    }
}

public data object ColorAsRgbaObjectSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor
        get() = ColorObjectSurrogate.RgbaColor.serializer().descriptor

    override fun deserialize(decoder: Decoder): Color {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = ColorObjectSurrogate.RgbaColor.serializer()
        )

        return Color(
            red = surrogate.red,
            green = surrogate.green,
            blue = surrogate.blue,
            alpha = surrogate.alpha
        )
    }

    override fun serialize(encoder: Encoder, value: Color) {
        val surrogate = ColorObjectSurrogate.RgbaColor(
            red = value.red,
            green = value.green,
            blue = value.blue,
            alpha = value.alpha
        )

        encoder.encodeSerializableValue(
            serializer = ColorObjectSurrogate.RgbaColor.serializer(),
            value = surrogate
        )
    }
}

public data object ColorAsULongObjectSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor
        get() = ColorObjectSurrogate.ULongColor.serializer().descriptor

    override fun deserialize(decoder: Decoder): Color {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = ColorObjectSurrogate.ULongColor.serializer()
        )

        return Color(surrogate.value)
    }

    override fun serialize(encoder: Encoder, value: Color) {
        val surrogate = ColorObjectSurrogate.ULongColor(value.value)

        encoder.encodeSerializableValue(
            serializer = ColorObjectSurrogate.ULongColor.serializer(),
            value = surrogate
        )
    }
}

public fun Color.Companion.argbObjectSerializer(): KSerializer<Color> =
    ColorAsArgbObjectSerializer

public fun Color.Companion.rgbaObjectSerializer(): KSerializer<Color> =
    ColorAsRgbaObjectSerializer

public fun Color.Companion.uLongObjectSerializer(): KSerializer<Color> =
    ColorAsULongObjectSerializer
