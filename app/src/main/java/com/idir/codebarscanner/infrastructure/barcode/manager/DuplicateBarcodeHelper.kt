package com.idir.codebarscanner.infrastructure.barcode.manager

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper
import java.util.*

class DuplicateBarcodeHelper(private val register:MutableMap<String,Int>,
                             private val activeGroups:MutableList<BarcodeGroup>,
                             private val editGroup:BarcodeGroup?) : IBarcodeHelper {

    override fun add(rawBarcode: String) {
        activeGroups.forEach { it.barcodes.put(rawBarcode, Barcode(rawBarcode, timeStamp(),0)) }
    }

    override fun addAll(rawBarcodes: List<String>) {
        val timeStamp = timeStamp()
        rawBarcodes.forEach {
            activeGroups.forEach { group -> group.barcodes.put(it,Barcode(it,timeStamp,0)) }
        }


    }

    override fun remove(barcode: Barcode,group: BarcodeGroup) {
        val key = group.id + barcode.value
        register.remove(key)
        editGroup!!.barcodes.remove(barcode.value)
    }

    private fun timeStamp(): String {
        return "${Calendar.DAY_OF_MONTH}/${Calendar.MONTH}/${Calendar.YEAR} +  ${Calendar.HOUR_OF_DAY}:${Calendar.MINUTE}:${Calendar.SECOND} "
    }

}