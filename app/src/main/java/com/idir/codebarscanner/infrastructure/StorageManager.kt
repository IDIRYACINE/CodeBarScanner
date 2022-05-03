package com.idir.codebarscanner.infrastructure

import android.content.Context
import android.util.Log
import com.idir.codebarscanner.infrastructure.serializers.MutableStateSerializer
import com.idir.codebarscanner.infrastructure.serializers.SnapshotStateListSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.util.*

class StorageManager {
    companion object{
        const val BARCODES_REGISTER_KEY = "barcodesRegister"
        const val GROUPS_REGISTER_KEY = "groupsRegister"
        const val GROUPS_KEY = "groups"

    }

    private fun saveToFile(content : String , fileName :String ){
        val file = File( fileName)
        if(file.exists()){
            try {
                val out = FileOutputStream(file)
                out.write(content.toByteArray())
            }
            catch (exception : Exception){
                Log.wtf("IDIRIDIR" , exception.stackTraceToString())
            }
        }
        else{
           file.createNewFile() }
    }

    fun saveSettings(context: Context, json:String){
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
        saveToFile(json , directory)
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadSettings(context : Context) : Settings{
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
        return try {
            val inputStream =  FileInputStream(directory)
            return Json.decodeFromStream(inputStream)

        } catch (exception:Exception){
            Settings()
        }

    }

    fun saveBarcodes(context: Context, json:String){
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_groups)
        saveToFile(json , directory)
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun loadBarcode(context : Context) : Map<String,Any>{
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_groups)
        return try {
            val inputStream =  FileInputStream(directory)
            val json : Map<String,JsonElement> = Json.decodeFromStream(inputStream)

            val registerSerializer =MapSerializer(String.serializer(),Int.serializer())
            val register: Map<String, Int> = decodeFromJsonElement(  registerSerializer ,json[GROUPS_REGISTER_KEY]!!)
            val barcodeRegister = decodeFromJsonElement(registerSerializer,json[BARCODES_REGISTER_KEY]!!)

            val groupSerializer = MapSerializer(String.serializer(),BarcodeGroup.serializer())
            val groups = decodeFromJsonElement(groupSerializer,json[GROUPS_KEY]!!)

            return mapOf(
                GROUPS_REGISTER_KEY to register.toMutableMap(),
                BARCODES_REGISTER_KEY to barcodeRegister.toMutableMap(),
                GROUPS_KEY to groups.toMutableMap()
            )

        } catch (exception:Exception){
            Log.wtf("IDIRIDR",exception.stackTraceToString())
            mapOf(
                GROUPS_REGISTER_KEY to mutableMapOf<String,Int>(),
                BARCODES_REGISTER_KEY to mutableMapOf<String,Int>(),
                GROUPS_KEY to mutableMapOf<String,BarcodeGroup>()
            )
        }

    }

}