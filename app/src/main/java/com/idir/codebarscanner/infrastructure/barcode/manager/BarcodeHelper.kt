package com.idir.codebarscanner.infrastructure.barcode.manager

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper
import java.util.*

class BarcodeHelper( private val register:MutableMap<String,Int>,
                    private val activeGroups:MutableList<BarcodeGroup>,
                    private val editGroup:BarcodeGroup?) : IBarcodeHelper {

    override fun add(rawBarcode: String) {
        activeGroups.forEach {
           checkBarcodeExistence(rawBarcode,it)
        }
    }

    override fun addAll(rawBarcodes: List<String>) {
        rawBarcodes.forEach {
            activeGroups.forEach {group ->
                checkBarcodeExistence(it,group)
            }
        }
    }

    override fun remove(barcode: Barcode,group: BarcodeGroup) {
        val key = group.id + barcode.value
        val count = register[key]!! - 1

        if(count < 1){
            register.remove(key)
        }

        editGroup!!.barcodes.remove(barcode.value)
    }

    private fun checkBarcodeExistence(rawBarcode:String,group: BarcodeGroup){
        val key = group.id + rawBarcode

        if(register.containsKey(key)){
            val count = register[key]!!   + 1

            group.barcodes[rawBarcode]?.count = count
            group.barcodes[rawBarcode]?.timestamp = timeStamp()
            register[key] = count
        }
        else{
            register[key] = 1
            group.barcodes[rawBarcode] = Barcode(rawBarcode,timeStamp(),1)
        }
    }

    private fun timeStamp(): String {
        return "${Calendar.DAY_OF_MONTH}/${Calendar.MONTH}/${Calendar.YEAR} ${Calendar.HOUR_OF_DAY}:${Calendar.MINUTE}:${Calendar.SECOND} "
    }
}