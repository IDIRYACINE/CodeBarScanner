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
) : IBarcodeGroupHelper(),IBarcodeSubscriber{

    private lateinit var barcodeHelper: IBarcodeHelper

    override fun add(groupName: String) {
        val groupKey :String = timeStamp()
        val group =  BarcodeGroup(groupKey,mutableStateOf(groupName), mutableMapOf(), mutableStateOf(false))
        data[groupKey] =group
        groups.add(group)
    }

    override fun remove(group: BarcodeGroup) {
            register.remove(group.id)
            groups.remove(group)
    }

    override fun subscribeToBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster) {
        broadcaster.subscribeToBarcodeStream(this)
    }

    override fun unsubscribeFromBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster) {
        broadcaster.unsubscribeFromBarcodeStream(this)
    }

    override fun setBarcodeHelper(helper: IBarcodeHelper) {
        barcodeHelper = helper
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