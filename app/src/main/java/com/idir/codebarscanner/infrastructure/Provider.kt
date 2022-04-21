package com.idir.codebarscanner.infrastructure

import com.idir.codebarscanner.application.HomeController


object Provider {

    var storageManager : StorageManager
        private set

    var homeController : HomeController
        private set

    init {
        storageManager = StorageManager()
        homeController = HomeController()

    }

}