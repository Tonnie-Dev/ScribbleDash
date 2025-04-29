package com.tonyxlab.scribbledash.data

import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

object OffsetSerializer : KSerializer<Offset> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Offset") {
            // After
            element("x", kotlinx.serialization.descriptors.PrimitiveSerialDescriptor("x", kotlinx.serialization.descriptors.PrimitiveKind.FLOAT))
            element("y", kotlinx.serialization.descriptors.PrimitiveSerialDescriptor("y", kotlinx.serialization.descriptors.PrimitiveKind.FLOAT))
        }

    override fun serialize(encoder: Encoder, value: Offset) {
        encoder.encodeStructure(descriptor) {
            encodeFloatElement(descriptor, 0, value.x)
            encodeFloatElement(descriptor, 1, value.y)
        }
    }

    override fun deserialize(decoder: Decoder): Offset {
        var x: Float? = null
        var y: Float? = null
        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0, 1 -> {
                        val value = decodeFloatElement(descriptor, index)
                        if (index == 0) x = value else y = value
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unknown index $index")
                }
            }
        }
        return Offset(
                x ?: throw SerializationException("x is missing"),
                y ?: throw SerializationException("y is missing")
        )
    }
}

// And for a List<Offset>
val OffsetListSerializer = ListSerializer(OffsetSerializer)

object ListOffsetSerializer : KSerializer<List<Offset>> {
    private val listSerializer = ListSerializer(OffsetSerializer)

    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: List<Offset>) {
        listSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): List<Offset> {
        return listSerializer.deserialize(decoder)
    }
}
