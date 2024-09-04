//[compose-serialization-core](../../../index.md)/[com.mooncloak.kodetools.compose.serialization](../index.md)/[AnnotatedStringRangeSerializer](index.md)

# AnnotatedStringRangeSerializer

[common]\
class [AnnotatedStringRangeSerializer](index.md)&lt;[T](index.md)&gt;(typeSerializer: KSerializer&lt;[T](index.md)&gt;) : KSerializer&lt;[AnnotatedString.Range](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/AnnotatedString.Range.html)&lt;[T](index.md)&gt;&gt;

## Constructors

| | |
|---|---|
| [AnnotatedStringRangeSerializer](-annotated-string-range-serializer.md) | [common]<br>constructor(typeSerializer: KSerializer&lt;[T](index.md)&gt;) |

## Properties

| Name | Summary |
|---|---|
| [descriptor](descriptor.md) | [common]<br>open override val [descriptor](descriptor.md): SerialDescriptor |

## Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | [common]<br>open override fun [deserialize](deserialize.md)(decoder: Decoder): [AnnotatedString.Range](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/AnnotatedString.Range.html)&lt;[T](index.md)&gt; |
| [serialize](serialize.md) | [common]<br>open override fun [serialize](serialize.md)(encoder: Encoder, value: [AnnotatedString.Range](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/AnnotatedString.Range.html)&lt;[T](index.md)&gt;) |
