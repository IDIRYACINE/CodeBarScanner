package com.idir.codebarscanner.infrastructure.serializers

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.idir.codebarscanner.data.Barcode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

///An issue with the KSerializer sourceCode can't use till they fix it
class SnapshotStateListSerializer <T>(private val dataSerializer: KSerializer<T>) :
    KSerializer<SnapshotStateList<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    override fun serialize(encoder: Encoder, value: SnapshotStateList<T>) = ListSerializer(dataSerializer).serialize(encoder,value.toList())
    override fun deserialize(decoder: Decoder): SnapshotStateList<T> = mutableStateListOf(dataSerializer.deserialize(decoder))
}