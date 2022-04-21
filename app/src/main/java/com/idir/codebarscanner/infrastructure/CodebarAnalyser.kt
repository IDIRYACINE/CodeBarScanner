package com.idir.codebarscanner.infrastructure

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.TimeUnit

@SuppressLint("UnsafeOptInUsageError")
class CodebarAnalyser(private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit) : ImageAnalysis.Analyzer {

    private var lastAnalyzedTimeStamp = 0L
    private val barcodeScanner : BarcodeScanner

    init {
        barcodeScanner = setUpBarcodeScanner()
    }

    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (minScreenshotIntervalPassed(currentTimestamp)) {
            image.image?.let { imageToAnalyze ->
                val imageToProcess = generateImageToProcess(imageToAnalyze,image)

                barcodeScanner.process(imageToProcess)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            onBarcodeDetected(barcodes)
                        } else {
                            Log.d("TAG", "analyze: No barcode Scanned")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("TAG", "BarcodeAnalyser: Something went wrong $exception")
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            }
            lastAnalyzedTimeStamp = currentTimestamp
        } else {
            image.close()
        }
    }

    private fun minScreenshotIntervalPassed(currentTimestamp: Long) =
        currentTimestamp - lastAnalyzedTimeStamp >= TimeUnit.SECONDS.toMillis(1)

    private fun setUpBarcodeScanner() : BarcodeScanner {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
        return BarcodeScanning.getClient(options)
    }

    private fun generateImageToProcess(imageToAnalyze : Image, image: ImageProxy): InputImage {
        return InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)
    }

}