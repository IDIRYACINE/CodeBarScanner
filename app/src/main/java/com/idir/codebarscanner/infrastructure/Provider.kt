package com.idir.codebarscanner.infrastructure

import android.content.Context
import com.idir.codebarscanner.application.CameraController
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.application.SettingsController
import com.idir.codebarscanner.infrastructure.codebar.BarcodeAnalyser
import com.idir.codebarscanner.infrastructure.codebar.BarcodeBroadcaster
import com.idir.codebarscanner.infrastructure.codebar.IBarcodeBroadcaster


object Provider {

    val storageManager : StorageManager = StorageManager()
    lateinit var httpManager : HttpManager

    lateinit var homeController : HomeController
    lateinit var settingsController : SettingsController
    lateinit var cameraController : CameraController

    val barcodeAnalyser : BarcodeAnalyser = BarcodeAnalyser()
    val barcodeBroadcaster : IBarcodeBroadcaster = BarcodeBroadcaster()

    fun initApp(context: Context){
        settingsController = SettingsController()
        settingsController.load(context)
        httpManager = HttpManager(settingsController.settings)
        homeController = HomeController()
        homeController.load(context)
        cameraController = CameraController(barcodeBroadcaster)
    }


}