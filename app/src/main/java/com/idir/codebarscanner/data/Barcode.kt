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
    val value:String ,
    val timestamp : String,
    var count : Int
    ){
    companion object{
        fun toMap(barcode:Barcode): JsonMap{
            return mapOf(
                "value" to Json.encodeToJsonElement(barcode.value),
                "timestamp" to Json.encodeToJsonElement(barcode.timestamp)
            )
        }
    }
}


@Serializable
data class BarcodeGroup(
    val id : String,
    val name:MutableState<String> ,
    val barcodes:MutableMap<String,Barcode>,
    val isActive : MutableState<Boolean>
    ) {
    companion object{
        fun toMap(group : BarcodeGroup):JsonMap {
            val temp = mutableListOf<JsonMap>()

           /* group.barcodes.forEach{
                barcode -> temp.add(barcode)
            }*/

            val name = Json.encodeToJsonElement(group.name.value)

            return mapOf(
                "name" to name,
                "barcodes" to Json.encodeToJsonElement(temp)
            )
        }

    }

}

data class BarcodeGroupEntry(val id:String, val name: MutableState<String>,val active:MutableState<Boolean>)














