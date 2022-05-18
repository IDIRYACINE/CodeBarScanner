package com.idir.codebarscanner.infrastructure.barcode.manager

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeBroadcaster
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeGroupHelper
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeSubscriber

class DuplicateGroupHelper (
    private val register:MutableMap<String,Int>,
    private val data:MutableMap<String, BarcodeGroup>,
    private val groups: SnapshotStateList<BarcodeGroup>,
    private val barcodeHelper: IBarcodeHelper
) : IBarcodeGroupHelper,IBarcodeSubscriber{

    override fun add(groupName: String) {

        if (!register.containsKey(groupName)){
            val group = BarcodeGroup(groupName,mutableStateOf(groupName), mutableMapOf(), mutableStateOf(false))
            register[groupName] = 0
            data[groupName] = group
            groups.add(group)
        }
        else{
            val groupId = groupName + register[groupName]
            val group = BarcodeGroup(groupId,mutableStateOf(groupName), mutableMapOf(), mutableStateOf(false))

            data[groupId] = group
            groups.add(group)
        }

    }

    override fun remove(groupEntry: BarcodeGroup) {
        val count = register[groupEntry.id]!! - 1
        register[groupEntry.id] = count

        if(count < 0){
            register.remove(groupEntry.id)
        }

        data.remove(groupEntry.id)
        groups.remove(groupEntry)
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