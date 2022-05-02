@file:UseSerializers(MutableStateSerializer::class)

package com.idir.codebarscanner.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.idir.codebarscanner.R
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.codebar.IBarcodeSubscriber
import com.idir.codebarscanner.infrastructure.serializers.MutableStateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class Barcode (
    val value:String ,
    val timestamp : String,
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
    val name:MutableState<String> ,
    val barcodes:MutableList<Barcode>,
    val isActive : MutableState<Boolean>
    ) : IBarcodeSubscriber {
    companion object{
        fun toMap(group : BarcodeGroup):JsonMap {
            val temp = mutableListOf<JsonMap>()

            group.barcodes.forEach{
                barcode -> temp.add(Barcode.toMap(barcode))
            }
            val name = Json.encodeToJsonElement(group.name.value)

            return mapOf(
                "name" to name,
                "barcodes" to Json.encodeToJsonElement(temp)
            )
        }

        fun save(context: Context,json:String){
            val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_groups)
            Provider.storageManager.saveToFile(json , directory)
        }

        fun load(context : Context) : List<BarcodeGroup>{
            val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_groups)
            return try {
                val raw : List<BarcodeGroup> = Provider.storageManager.loadFromFile(directory)
                raw
            } catch (exception:Exception){
                emptyList()
            }
        }
    }

    override fun getId(): String {
        return name.value
    }

    override fun notify(barcode: Barcode) {
        barcodes.add(barcode)
    }

    override fun notify(barcode: List<Barcode>) {
        barcodes.addAll(barcode)
    }

    override fun getValue(): BarcodeGroup {
        return this
    }
}















