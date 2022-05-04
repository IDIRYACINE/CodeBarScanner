@file:Suppress("UNCHECKED_CAST")

package com.idir.codebarscanner.infrastructure

import android.content.Context
import android.widget.Toast
import com.idir.codebarscanner.application.CameraController
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.application.SettingsController
import com.idir.codebarscanner.infrastructure.barcode.*
import com.idir.codebarscanner.infrastructure.barcode.manager.BarcodeManager


object Provider {

    val storageManager : StorageManager = StorageManager()
    lateinit var httpManager : HttpManager
    lateinit var barcodesManager :IBarcodeManager
    lateinit var cameraAnalyser: ICameraAnalyser

    lateinit var homeController : HomeController
    lateinit var settingsController : SettingsController
    lateinit var cameraController : CameraController

    lateinit var toaster : IBarcodeSubscriber

    val barcodeBroadcaster : IBarcodeBroadcaster = BarcodeBroadcaster()

    fun initApp(context: Context){

        val tempManager = BarcodeManager()
        tempManager.load(context, storageManager)
        tempManager.setBarcodeDuplicateMode(false)
        tempManager.setGroupDuplicateMode(false)
        barcodesManager = tempManager

        cameraAnalyser = CameraAnalyser()

        toaster = Toaster(context)
        barcodeBroadcaster.subscribeToBarcodeStream(toaster)

        settingsController = SettingsController()
        settingsController.load(context)
        httpManager = HttpManager(settingsController.settings)

        homeController = HomeController(barcodesManager)

        cameraController = CameraController(cameraAnalyser)
    }


}

class Toaster(private val context: Context) : IBarcodeSubscriber{

    override fun getId(): String {
       return ""
    }

    override fun notify(rawBarcode: String) {
        Toast.makeText(context, rawBarcode, Toast.LENGTH_SHORT).show()
    }

    override fun notify(rawBarcodes: List<String>) {
        rawBarcodes.forEach {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

}