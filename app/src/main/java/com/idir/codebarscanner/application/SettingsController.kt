package com.idir.codebarscanner.application

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.ViewModel
import com.idir.codebarscanner.R
import com.idir.codebarscanner.infrastructure.StorageManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Settings(var host:String , var username:String , var password:String)

class SettingsController() : ViewModel() {

    lateinit var settings : Settings
        private set

    val manager : StorageManager = StorageManager()

    fun load(context: Context) {
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)

        try {
            settings = manager.loadFromFile(directory)
        }
        catch (excetion:Exception){
            settings = Settings("","","")
        }
    }

    fun save(context:Context){
        val json = Json.encodeToString(settings)
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
        manager.saveToFile(json , directory)
    }
}