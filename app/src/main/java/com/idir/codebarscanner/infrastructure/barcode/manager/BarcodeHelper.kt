package com.idir.codebarscanner.infrastructure.barcode.manager

import android.util.Log
import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper


class BarcodeHelper(private val register:MutableMap<String,Int>,
                    private val activeGroups:MutableList<BarcodeGroup>) : IBarcodeHelper() {

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


    private fun checkBarcodeExistence(rawBarcode:String,group: BarcodeGroup){
        Log.wtf("Normal",register.toString())
        Log.wtf("Normal",group.barcodes.toString())

        val key = getEntryKey(rawBarcode,group.id)
        val barcodeKey = "${key}_1"

        if(register.containsKey(key)){
            var count = register[key]!! + 1
            register[key] = count
            if(register.containsKey(barcodeKey)){
                count = register[barcodeKey]!! + 1
                register[barcodeKey] = count

                group.barcodes[barcodeKey]!!.count = count
                group.barcodes[barcodeKey]!!.time = currentTime()

            }
            else{
                register[barcodeKey] = 1
                group.barcodes[barcodeKey] = Barcode(barcodeKey,rawBarcode,currentTime(),1)
            }
        }
        else{
            register[key] = 1
            register[barcodeKey] = 1
            group.barcodes[barcodeKey] = Barcode(barcodeKey,rawBarcode,currentTime(),1)
        }
        Log.wtf("BarcodeHelper",register.toString())


    }

}