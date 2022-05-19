package com.idir.codebarscanner.infrastructure.barcode.manager

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper


class BarcodeHelper(
    private val groupsRegister:MutableMap<String,MutableMap<String,Int>>,
    private val activeGroups:MutableList<BarcodeGroup>) : IBarcodeHelper() {

    private lateinit var register :MutableMap<String,Int>


    override fun add(rawBarcode: String) {
        activeGroups.forEach {
            register = groupsRegister[it.id]!!
           checkBarcodeExistence(rawBarcode,it)
        }
    }

    override fun addAll(rawBarcodes: List<String>) {
        rawBarcodes.forEach {
            activeGroups.forEach {group ->
                register = groupsRegister[group.id]!!
                checkBarcodeExistence(it,group)
            }
        }
    }


    override fun remove(
        barcode: Barcode,
        group: BarcodeGroup,
        iterator: MutableIterator<MutableMap.MutableEntry<String, Barcode>>,

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


    private fun checkBarcodeExistence(rawBarcode:String,group: BarcodeGroup){
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

    }

}