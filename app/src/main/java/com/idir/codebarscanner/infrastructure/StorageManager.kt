package com.idir.codebarscanner.infrastructure

import android.util.Log
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class StorageManager {

    fun saveToFile(content : String , fileName :String ){
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
           file.createNewFile()
        }

    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> loadFromFile(fileName: String) : T {
        val inputStream =  FileInputStream(fileName)
        return Json.decodeFromStream(inputStream)
    }

}