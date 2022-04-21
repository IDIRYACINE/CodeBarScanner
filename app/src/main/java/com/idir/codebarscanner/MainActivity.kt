package com.idir.codebarscanner

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.idir.codebarscanner.ui.components.AppBar
import com.idir.codebarscanner.ui.components.AttributeRow
import com.idir.codebarscanner.ui.components.BottomNavigationBar
import com.idir.codebarscanner.ui.components.NavigationGraph
import com.idir.codebarscanner.ui.screens.CameraActivity
import com.idir.codebarscanner.ui.theme.CodeBarScannerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeBarScannerTheme {
                App()
            }
        }
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



