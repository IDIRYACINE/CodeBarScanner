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
        private set
    lateinit var barcodesManager :IBarcodeManager
        private set
    lateinit var cameraAnalyser: ICameraAnalyser
        private set

    lateinit var homeController : HomeController
        private set
    lateinit var settingsController : SettingsController
        private set
    lateinit var cameraController : CameraController
        private set

    lateinit var toaster : IBarcodeSubscriber
        private set
    lateinit var resourceLoader:ResourcesLoader
        private set
    val barcodeBroadcaster : IBarcodeBroadcaster = BarcodeBroadcaster()

    fun initApp(context: Context){

        val tempManager = BarcodeManager()
        tempManager.load(context, storageManager)
        tempManager.setBarcodeDuplicateMode(false)
        tempManager.setGroupDuplicateMode(false)
        barcodesManager = tempManager

        resourceLoader = ResourcesLoader(context)

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