package com.idir.codebarscanner.infrastructure.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.Settings

class DataSaverService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val context = applicationContext
        val extras = intent.extras!!
        val settings =  extras.getString("Settings")!!
        val barcodes =  extras.getString("Barcodes")!!

        Settings.save(context, settings)
        BarcodeGroup.save(context,barcodes)
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}