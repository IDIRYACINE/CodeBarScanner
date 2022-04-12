package com.idir.codebarscanner.ui.screens

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.idir.codebarscanner.R

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(){
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
        val permissionFailedMessage = stringResource(R.string.cam_permission_fail)

        Button(onClick = {
            cameraPermissionState.launchPermissionRequest()
            if(cameraPermissionState.hasPermission){
                context.startActivity(Intent(context,CameraActivity::class.java))

            }else{
                Toast.makeText(context, permissionFailedMessage, Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(stringResource(R.string.scan_single_button))
        }
    }
}