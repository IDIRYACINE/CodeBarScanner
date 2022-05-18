@file:UseSerializers(MutableStateSerializer::class)

package com.idir.codebarscanner.data

import androidx.compose.runtime.MutableState
import com.idir.codebarscanner.infrastructure.serializers.MutableStateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class Barcode (
    val value:String,
    var timestamp : String,
    var count : Int
    )


@Serializable
data class BarcodeGroup(
    val id : String,
    val name:MutableState<String> ,
    val barcodes:MutableMap<String,Barcode>,
    val isActive : MutableState<Boolean>
    )
















