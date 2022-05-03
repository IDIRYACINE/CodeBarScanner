package com.idir.codebarscanner.infrastructure.barcode.manager

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.BarcodeGroupEntry
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

        if(count < 0){
            register.remove(key)
        }

        editGroup!!.barcodes.remove(barcode.value)
    }

    private fun checkBarcodeExistence(rawBarcode:String,group: BarcodeGroup){
        val key = group.id + rawBarcode

        if(register.containsKey(key)){
            val count = register[key]!! + 1
            group.barcodes[rawBarcode]!!.count = count
            register[key] = count
        }
        else{
            register[key] = 0
            group.barcodes[rawBarcode] = Barcode(rawBarcode,timeStamp(),0)
        }
    }

    private fun timeStamp(): String {
        return Calendar.getInstance().time.toString()
    }
}