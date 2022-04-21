
package com.idir.codebarscanner.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Barcode (
    val value:String ,
    val timestamp : String,
    )

data class BarcodeGroup(
    val name:MutableState<String> ,
    val barcodes:SnapshotStateList<Barcode>,
    val isActive : MutableState<Boolean>
    )









