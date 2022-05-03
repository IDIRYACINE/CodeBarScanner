package com.idir.codebarscanner.infrastructure.barcode.manager

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.BarcodeGroupEntry
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeBroadcaster
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeGroupHelper
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeSubscriber


class GroupHelper (
    private val register:MutableMap<String,Int>,
    private val data:MutableMap<String,BarcodeGroup>,
    private val groups:SnapshotStateList<BarcodeGroup>,
    private val  barcodeHelper: IBarcodeHelper
) : IBarcodeGroupHelper , IBarcodeSubscriber{

    override fun remove(groupEntry: BarcodeGroup){
        data.remove(groupEntry.id)
        register.remove(groupEntry.id)
        groups.remove(groupEntry)
    }

    override fun add(groupName: String){
        if (!register.containsKey(groupName)){
            register[groupName] = 0
            val group = BarcodeGroup(groupName,mutableStateOf(groupName), mutableMapOf(), mutableStateOf(false))
            data[groupName] = group
            groups.add(group)
        }else{
            val count = register[groupName]!! + 1
            register[groupName] = count
        }
    }

    override fun subscribeToBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster) {
        broadcaster.subscribeToBarcodeStream(this)
    }

    override fun unsubscribeFromBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster) {
        broadcaster.unsubscribeFromBarcodeStream(this)
    }

    override fun getId(): String {
        return ""
    }

    override fun notify(rawBarcode: String) {
        barcodeHelper.add(rawBarcode)
    }

    override fun notify(rawBarcodes: List<String>) {
        barcodeHelper.addAll(rawBarcodes)
    }


}