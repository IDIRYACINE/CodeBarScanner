package com.idir.codebarscanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
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
import com.idir.codebarscanner.infrastructure.HttpManager
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.services.ServiceBroadcaster
import com.idir.codebarscanner.ui.components.BottomNavigationBar
import com.idir.codebarscanner.ui.components.NavigationGraph
import com.idir.codebarscanner.ui.theme.CodeBarScannerTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    private lateinit var context : Context

    private lateinit var handler :Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           SplashScreen()
        }
        initApp()

    }

    private fun initApp(){
        context = App.appInstance.applicationContext

        handler = Handler(Looper.getMainLooper()) {
            if(it.what == HttpManager.SUCCESS){
                val resources = context.resources
                Toast.makeText(context, resources.getString(R.string.send_data_failed), Toast.LENGTH_LONG).show()
            }
            else{
                val resources = context.resources
                Toast.makeText(context, resources.getString(R.string.send_data_failed), Toast.LENGTH_LONG).show()
            }
            return@Handler true
        }
        Provider.initApp(this)
        setContent {
            CodeBarScannerTheme {
                App(handler)
            }
        }
    }

    override fun onStop() {
        val settings : String = Json.encodeToString(Provider.settingsController.settings)
        val groups : String = Provider.barcodesManager.encodeToJson()

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
fun App(handler: Handler) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ){
            innerPadding ->  Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(navController = navController,handler)
            }
    }
}

@Composable
fun SplashScreen(){
    Surface(color = Blue) {
        Text(text = "loading")
    }
}

