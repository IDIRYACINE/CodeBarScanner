package com.idir.codebarscanner.application

import android.content.Context
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.idir.codebarscanner.data.Barcode as AppBarcode
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeBroadcaster
import java.util.*

class CameraController(
    private val barcodeBroadcaster: IBarcodeBroadcaster
) {

    private fun onBarcodeDetected(rawBarcode : String ){
        barcodeBroadcaster.notifyBarcode(rawBarcode)
    }

    fun googleVisionBarcodeHelper(barcodes : List<Barcode> , context: Context){
        barcodes.forEach { barcode ->
            barcode.rawValue?.let { barcodeValue ->
              onBarcodeDetected(barcodeValue)
                Toast.makeText(context, barcodeValue, Toast.LENGTH_SHORT).show()
            }
        }
    }

}