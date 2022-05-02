package com.idir.codebarscanner.application

import android.content.Context
import androidx.compose.runtime.mutableStateOf
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

    fun load(context: Context) {
        settings = Settings.load(context)
    }


}