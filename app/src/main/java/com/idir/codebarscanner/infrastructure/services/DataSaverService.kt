package com.idir.codebarscanner.infrastructure.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.idir.codebarscanner.infrastructure.StorageManager

class DataSaverService : Service() {
    private val storageManager : StorageManager = StorageManager()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val context = applicationContext
        val extras = intent.extras!!

        val settings =  extras.getString("Settings")!!
        val barcodes =  extras.getString("Barcodes")!!

        storageManager.saveSettings(context, settings)
        storageManager.saveBarcodes(context,barcodes)

        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}