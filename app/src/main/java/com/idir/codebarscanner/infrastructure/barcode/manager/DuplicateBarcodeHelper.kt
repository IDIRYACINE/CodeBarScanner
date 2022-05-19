package com.idir.codebarscanner.infrastructure.barcode.manager

import android.util.Log
import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper
import java.util.*

class DuplicateBarcodeHelper(private val register:MutableMap<String,Int>,
                             private val activeGroups:MutableList<BarcodeGroup>) : IBarcodeHelper() {

    override fun add(rawBarcode: String) {
        activeGroups.forEach {
            generateBarcodeEntry(rawBarcode,it)
        }
    }

    override fun addAll(rawBarcodes: List<String>) {
        rawBarcodes.forEach {
            activeGroups.forEach {
                    group -> generateBarcodeEntry(it,group)
            }
        }
    }

    override fun remove(
        barcode: Barcode,
        group: BarcodeGroup,
        iterator: MutableIterator<MutableMap.MutableEntry<String, Barcode>>,
        purge: Boolean
    ) {
        fun updateRegisterKeyCount(key:String , step:Int){
            if(register.containsKey(key)){
                val count = register[key]!! - step

                if (count < 1){
                    register.remove(key)
                }
                else{
                    register[key] = count
                }
            }
        }

        val key = getEntryKey(barcode.value,group.name.value)
        var step = 1
        if(purge && register.containsKey(key)){
            step = register[key]!!
        }

        updateRegisterKeyCount(barcode.id,step)
        updateRegisterKeyCount(key,step)

        iterator.remove()
    }


    private fun generateBarcodeEntry(rawBarcode:String,group: BarcodeGroup){
        Log.wtf("DuplicateH",register.toString())
        Log.wtf("DuplicateH",group.barcodes.toString())

        val key = getEntryKey(rawBarcode,group.id)
        if(register.containsKey(key)){
            register[key] = register[key]!! + 1
        }
        else{
            register[key] = 1
        }

        val barcodeKey = timeStamp()
        group.barcodes[barcodeKey] = Barcode(barcodeKey,rawBarcode,currentTime(),1)

        Log.wtf("BarcodeDuplicateHelper",register.toString())
    }

}