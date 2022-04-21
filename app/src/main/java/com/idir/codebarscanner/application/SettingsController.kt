package com.idir.codebarscanner.application

import android.content.Context
import androidx.lifecycle.ViewModel
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.Settings
import com.idir.codebarscanner.infrastructure.StorageManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.idir.codebarscanner.infrastructure.Provider


class SettingsController() : ViewModel() {

    lateinit var settings : Settings
        private set

    val manager : StorageManager

    init{
        manager = Provider.storageManager
    }


    fun load(context: Context) {
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
        try {
            settings = manager.loadFromFile(directory)
        }
        catch (excetion:Exception){
            settings = Settings()
        }
    }

    fun save(context:Context){
        val json = Json.encodeToString(settings)
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_settings)
        manager.saveToFile(json , directory)
    }

}