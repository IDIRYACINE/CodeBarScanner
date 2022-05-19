package com.idir.codebarscanner.infrastructure.barcode.manager

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper

class DuplicateBarcodeHelper(
    private val groupsRegister:MutableMap<String,MutableMap<String,Int>>,
    private val activeGroups:MutableList<BarcodeGroup>) : IBarcodeHelper() {

    private lateinit var register :MutableMap<String,Int>

    override fun add(rawBarcode: String) {
        activeGroups.forEach {
            register = groupsRegister[it.id]!!
            generateBarcodeEntry(rawBarcode,it)
        }
    }

    override fun addAll(rawBarcodes: List<String>) {
        rawBarcodes.forEach {
            activeGroups.forEach {
                    group ->
                register = groupsRegister[group.id]!!
                generateBarcodeEntry(it,group)
            }
        }
    }

    override fun remove(
        barcode: Barcode,
        group: BarcodeGroup,
        iterator: MutableIterator<MutableMap.MutableEntry<String, Barcode>>
    ) {
        fun updateRegisterKeyCount(key:String , ){
            if(register.containsKey(key)){
                val count = register[key]!! - 1
                    if (count < 1){
                        register.remove(key)
                    }
                    else{
                        register[key] = count
                    }

            }
        }

        val key = getEntryKey(barcode.value,group.name.value)

        updateRegisterKeyCount(barcode.id)
        updateRegisterKeyCount(key)

        iterator.remove()
    }


    private fun generateBarcodeEntry(rawBarcode:String,group: BarcodeGroup){

        val key = getEntryKey(rawBarcode,group.id)
        if(register.containsKey(key)){
            register[key] = register[key]!! + 1
        }
        else{
            register[key] = 1
        }

        val barcodeKey = timeStamp()
        group.barcodes[barcodeKey] = Barcode(barcodeKey,rawBarcode,currentTime(),1)

    }

}