package com.idir.codebarscanner.infrastructure.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class ServiceBroadcaster : BroadcastReceiver() {
    companion object{
        const val SETTINGS_KEY = "Settings"
        const val BARCODE_KEY = "Barcodes"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val dataSaver = Intent(context,DataSaverService::class.java)
        val bundle = Bundle()

        Log.wtf("IDIRIDIR","RECEIVER")

        passSerializable(intent,bundle,SETTINGS_KEY)
        passSerializable(intent,bundle,BARCODE_KEY)
        dataSaver.putExtras(bundle)

        context.startService(dataSaver)
    }

    private fun passSerializable(src:Intent,out: Bundle,key:String) {
        out.putSerializable(key , src.getSerializableExtra(key))
    }
}