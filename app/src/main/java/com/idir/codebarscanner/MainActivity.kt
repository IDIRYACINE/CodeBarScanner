package com.idir.codebarscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.BottomNavigationBar
import com.idir.codebarscanner.ui.components.NavigationGraph
import com.idir.codebarscanner.ui.theme.CodeBarScannerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Provider.initApp(this)
        setContent {
            CodeBarScannerTheme {
                App()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Provider.saveAll(this)
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



