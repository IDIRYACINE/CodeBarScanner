@file:Suppress("UNCHECKED_CAST")

package com.idir.codebarscanner.infrastructure

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.idir.codebarscanner.application.CameraController
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.application.SettingsController
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.BarcodeAnalyser
import com.idir.codebarscanner.infrastructure.barcode.BarcodeBroadcaster
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeBroadcaster
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeManager
import com.idir.codebarscanner.infrastructure.barcode.manager.BarcodeManager


object Provider {

    val storageManager : StorageManager = StorageManager()
    lateinit var httpManager : HttpManager
    lateinit var barcodesManager :IBarcodeManager
    lateinit var homeController : HomeController
    lateinit var settingsController : SettingsController
    lateinit var cameraController : CameraController

    val barcodeAnalyser : BarcodeAnalyser = BarcodeAnalyser()
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


        cameraController = CameraController(barcodeBroadcaster)
    }


}