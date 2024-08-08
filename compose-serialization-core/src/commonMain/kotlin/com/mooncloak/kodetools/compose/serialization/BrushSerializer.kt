package com.mooncloak.kodetools.compose.serialization

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Brush.Companion.sweepGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal sealed interface BrushSurrogate {

    @Serializable
    @SerialName(value = "solid")
    class SolidColor internal constructor(
        @SerialName(value = "value") @Serializable(with = ColorAsULongSerializer::class) val value: Color
    ) : BrushSurrogate

    @Serializable
    @SerialName(value = "linear_gradient")
    class LinearGradient internal constructor(
        @SerialName(value = "colors") val colors: List<@Serializable(with = ColorAsULongSerializer::class) Color>,
        @SerialName(value = "stops") val stops: List<Float>? = null,
        @SerialName(value = "start") @Serializable(with = OffsetSerializer::class) val start: Offset,
        @SerialName(value = "end") @Serializable(with = OffsetSerializer::class) val end: Offset,
        @SerialName(value = "tile_mode") @Serializable(with = TileModeAsStringSerializer::class) val tileMode: TileMode = TileMode.Clamp
    ) : BrushSurrogate

    @Serializable
    @SerialName(value = "radial_gradient")
    class RadialGradient internal constructor(
        @SerialName(value = "colors") val colors: List<@Serializable(with = ColorAsULongSerializer::class) Color>,
        @SerialName(value = "stops") val stops: List<Float>? = null,
        @SerialName(value = "center") @Serializable(with = OffsetSerializer::class) val center: Offset,
        @SerialName(value = "radius") val radius: Float,
        @SerialName(value = "tile_mode") @Serializable(with = TileModeAsStringSerializer::class) val tileMode: TileMode = TileMode.Clamp
    ) : BrushSurrogate

    @Serializable
    @SerialName(value = "sweep_gradient")
    class SweepGradient internal constructor(
        @SerialName(value = "center") @Serializable(with = OffsetSerializer::class) val center: Offset,
        @SerialName(value = "colors") val colors: List<@Serializable(with = ColorAsULongSerializer::class) Color>,
        @SerialName(value = "stops") val stops: List<Float>? = null
    ) : BrushSurrogate
}

public data object BrushSerializer : KSerializer<Brush> {

    override val descriptor: SerialDescriptor
        get() = BrushSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): Brush {
        val surrogate = decoder.decodeSerializableValue(
            deserializer = BrushSurrogate.serializer()
        )

        return when (surrogate) {
            is BrushSurrogate.SolidColor -> SolidColor(value = surrogate.value)

            is BrushSurrogate.LinearGradient -> if (surrogate.stops == null) {
                linearGradient(
                    colors = surrogate.colors,
                    start = surrogate.start,
                    end = surrogate.end,
                    tileMode = surrogate.tileMode
                )
            } else {
                linearGradient(
                    colorStops = surrogate.stops.mapIndexed { index, stop ->
                        stop to surrogate.colors[index]
                    }.toTypedArray(),
                    start = surrogate.start,
                    end = surrogate.end,
                    tileMode = surrogate.tileMode
                )
            }

            is BrushSurrogate.RadialGradient -> if (surrogate.stops == null) {
                radialGradient(
                    colors = surrogate.colors,
                    center = surrogate.center,
                    radius = surrogate.radius,
                    tileMode = surrogate.tileMode
                )
            } else {
                radialGradient(
                    colorStops = surrogate.stops.mapIndexed { index, stop ->
                        stop to surrogate.colors[index]
                    }.toTypedArray(),
                    center = surrogate.center,
                    radius = surrogate.radius,
                    tileMode = surrogate.tileMode
                )
            }

            is BrushSurrogate.SweepGradient -> if (surrogate.stops == null) {
                sweepGradient(
                    colors = surrogate.colors,
                    center = surrogate.center
                )
            } else {
                sweepGradient(
                    colorStops = surrogate.stops.mapIndexed { index, stop ->
                        stop to surrogate.colors[index]
                    }.toTypedArray(),
                    center = surrogate.center
                )
            }
        }
    }

    override fun serialize(encoder: Encoder, value: Brush) {
        val surrogate = when (value) {
            is SolidColor -> BrushSurrogate.SolidColor(value = value.value)

            else -> error("Only the 'SolidColor' Brush type is supported for serialization, the other types don't expose their properties for serialization.")
        }

        encoder.encodeSerializableValue(
            serializer = BrushSurrogate.serializer(),
            value = surrogate
        )
    }
}

public fun Brush.Companion.serializer(): KSerializer<Brush> =
    BrushSerializer
