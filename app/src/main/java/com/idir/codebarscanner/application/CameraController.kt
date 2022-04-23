package com.idir.codebarscanner.application

import android.content.Context
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.idir.codebarscanner.data.Barcode as AppBarcode
import com.idir.codebarscanner.infrastructure.codebar.IBarcodeBroadcaster
import java.util.*

class CameraController(
    private val barcodeBroadcaster: IBarcodeBroadcaster
) {

    private fun onBarcodeDetected(barcode : AppBarcode ){
        barcodeBroadcaster.notifyBarcode(barcode)
    }

    fun googleVisionBarcodeHelper(barcodes : List<Barcode> , context: Context){
        barcodes.forEach { barcode ->
            barcode.rawValue?.let { barcodeValue ->
              onBarcodeDetected(AppBarcode(barcodeValue,timeStamp()))
                Toast.makeText(context, barcodeValue, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun timeStamp(): String {
        return Calendar.getInstance().time.toString()
    }

}