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
    var preview by remember { mutableStateOf<Preview?>(null) }
    var cameraSelector : CameraSelector

    AndroidView(
        factory = { AndroidViewContext -> PreviewView(AndroidViewContext) },
        modifier = Modifier.fillMaxSize(),
        update = {
                previewView ->

                cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        buildImageAnalyser(controller,context)
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        IconButton(
            onClick = {
                Toast.makeText(context, "Back Clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back arrow",
                tint = MaterialTheme.colors.surface
            )
        }
    }
}

private fun buildImageAnalyser(controller : CameraController , context : Context): ImageAnalysis {
    val barcodeAnalyser = Provider.barcodeAnalyser
    barcodeAnalyser.setOnBarcodeDetected { controller.googleVisionBarcodeHelper(it,context) }
    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    return ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also {
            it.setAnalyzer(cameraExecutor, barcodeAnalyser)
        }
}