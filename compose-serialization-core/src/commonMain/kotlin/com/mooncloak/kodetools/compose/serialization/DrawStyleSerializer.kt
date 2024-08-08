package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal sealed interface DrawStyleSurrogate {

    @Serializable
    @SerialName(value = "fill")
    data object Fill : DrawStyleSurrogate

    @Serializable
    @SerialName(value = "stroke")
    class Stroke internal constructor(
        @SerialName(value = "width") val width: Float,
        @SerialName(value = "miter") val miter: Float,
        @SerialName(value = "cap") @Serializable(with = StrokeCapAsStringSerializer::class) val cap: StrokeCap,
        @SerialName(value = "join") @Serializable(with = StrokeJoinAsStringSerializer::class) val join: StrokeJoin
    ) : DrawStyleSurrogate
}

public data object DrawStyleSerializer : KSerializer<DrawStyle> {

    override val descriptor: SerialDescriptor
        get() = DrawStyleSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): DrawStyle {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = DrawStyleSurrogate.serializer()
        )

        return when (surrogate) {
            is DrawStyleSurrogate.Fill -> Fill
            is DrawStyleSurrogate.Stroke -> Stroke(
                width = surrogate.width,
                miter = surrogate.miter,
                cap = surrogate.cap,
                join = surrogate.join,
                pathEffect = null
            )
        }
    }

    override fun serialize(encoder: Encoder, value: DrawStyle) {
        val surrogate = when (value) {
            is Fill -> DrawStyleSurrogate.Fill
            is Stroke -> DrawStyleSurrogate.Stroke(
                width = value.width,
                miter = value.miter,
                cap = value.cap,
                join = value.join
            )
        }

        encoder.encodeSerializableValue(
            serializer = DrawStyleSurrogate.serializer(),
            value = surrogate
        )
    }
}
