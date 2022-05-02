package com.idir.codebarscanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.services.ServiceBroadcaster
import com.idir.codebarscanner.ui.components.BottomNavigationBar
import com.idir.codebarscanner.ui.components.NavigationGraph
import com.idir.codebarscanner.ui.theme.CodeBarScannerTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           SplashScreen()
        }
        initApp()

    }

    private fun initApp(){
        Provider.initApp(this)
        setContent {
            CodeBarScannerTheme {
                App()
            }
        }
    }

    override fun onStop() {
        val settings : String = Json.encodeToString(Provider.settingsController.settings)
        val groups : String = Json.encodeToString(Provider.homeController.barcodes.toList())

        val data = Bundle()

        data.putString(ServiceBroadcaster.SETTINGS_KEY,settings)
        data.putString(ServiceBroadcaster.BARCODE_KEY,groups)

        val saveDataIntent = Intent(this,ServiceBroadcaster::class.java)
        saveDataIntent.putExtras(data)
        sendBroadcast(saveDataIntent)

        super.onStop()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ){
            innerPadding ->  Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(navController = navController)
            }
    }
}

@Composable
fun SplashScreen(){
    Surface(color = Blue) {
        Text(text = "loading")
    }
}

