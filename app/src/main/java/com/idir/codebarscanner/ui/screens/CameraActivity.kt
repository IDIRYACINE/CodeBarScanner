package com.idir.codebarscanner.ui.screens

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import com.idir.codebarscanner.R
import com.idir.codebarscanner.application.CameraController
import com.idir.codebarscanner.data.CameraIcons
import com.idir.codebarscanner.infrastructure.Provider
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(){
    val context = LocalContext.current

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val permissionFailedMessage = stringResource(R.string.cam_permission_fail)


    if(cameraPermissionState.hasPermission){
        CameraPreview()

    }else{
        Toast.makeText(context, permissionFailedMessage, Toast.LENGTH_SHORT).show()

        Button(onClick = {
            cameraPermissionState.launchPermissionRequest()

        },
        content = {Text("Request Permissions")}
        )
    }

}


@Composable
fun CameraPreview(controller: CameraController = Provider.cameraController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val isAnalysing = controller.getAnalysisState()
    Box {
    AndroidView(
        factory = { AndroidViewContext -> PreviewView(AndroidViewContext) },
        modifier = Modifier.fillMaxSize(),
        update = { previewView -> controller.setupCameraPreview(previewView,lifecycleOwner,context) }
    )

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(40.dp)
    ) {
        IconButton(
            onClick = {
                controller.toggleCameraFlash()
            }
        ) {
            Icon(
                imageVector = CameraIcons.Flash.icon,
                contentDescription = null,
                modifier = Modifier.scale(3F)
            )
        }
        IconButton(
            onClick = {
                controller.toggleAnalysisState()
            }
        ) {
            Icon(imageVector = CameraIcons.Analyse.icon,
                tint = if(isAnalysing.value) Color.Red else Color.Green ,
                modifier = Modifier.scale(3F),
                contentDescription = null)
        }
    }

    }

}
