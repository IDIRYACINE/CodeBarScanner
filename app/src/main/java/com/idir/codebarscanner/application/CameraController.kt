package com.idir.codebarscanner.application

import android.content.Context
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode

class CameraController(
    private val barcodes : MutableMap<String,String> = mutableMapOf() ,
    private var context: Context
) {

    private fun onBarcodeDetected(barcode : String ){
        if(!barcodes.containsKey(barcode)) {
             barcodes[barcode] = barcode
        }

        Toast.makeText(context, barcode, Toast.LENGTH_SHORT).show()
    }

    fun googleVisionBarcodeHelper(barcodes : List<Barcode>){
        barcodes.forEach { barcode ->
            barcode.rawValue?.let { barcodeValue ->
              onBarcodeDetected(barcodeValue)
            }
        }
    }

}