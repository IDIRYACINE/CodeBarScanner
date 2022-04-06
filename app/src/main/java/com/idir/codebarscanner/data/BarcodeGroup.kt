@file:Suppress("UNCHECKED_CAST")

package com.idir.codebarscanner.data

import androidx.compose.runtime.saveable.mapSaver

data class BarcodeGroup(val name:String , val barcodes:List<String>)

val BarcodeGroupSaver = run {
    val nameKey = "Name"
    val barcodesKey = "Barcodes"

    mapSaver(
        save = { mapOf(nameKey to it.name,barcodesKey to  it.barcodes) },
        restore = { BarcodeGroup(it[nameKey] as String, it[barcodesKey] as List<String>) }
    )
}





