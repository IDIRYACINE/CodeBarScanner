package com.idir.codebarscanner.infrastructure.barcode

import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.BarcodeGroupEntry
import com.idir.codebarscanner.data.JsonMap

interface IBarcodeBroadcaster {
    fun notifyBarcode(rawBarcode : String)
    fun notifyBarcodes(rawBarcodes : List<String>)
    fun subscribeToBarcodeStream(subscriber : IBarcodeSubscriber)
    fun unsubscribeFromBarcodeStream(subscriber : IBarcodeSubscriber)
    fun registerOnNotifyCommand(command:ICommand)
    fun unregisterOnNotifyCommand(command: ICommand)
}

interface IBarcodeSubscriber{
    fun getId() : String
    fun notify(rawBarcode : String)
    fun notify(rawBarcodes : List<String>)
}

interface IBarcodeManager{
    fun addGroup(groupName :String)
    fun editGroup(newName:String)
    fun removeGroup(groupEntry:BarcodeGroup)
    fun setActiveGroup(groupEntry: BarcodeGroup)
    fun getActiveGroup() : BarcodeGroup
    fun getGroups() : List<BarcodeGroup>
    fun setGroupDuplicateMode(value:Boolean)
    fun setBarcodeDuplicateMode(value:Boolean)
    fun toggleGroup(group: BarcodeGroup,value: Boolean)
    fun encodeToJson() : String
    fun encodeActiveToJson() : String
}

interface  IBarcodeGroupHelper{
    fun add(groupName: String)
    fun remove(groupEntry: BarcodeGroup)
    fun subscribeToBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster)
    fun unsubscribeFromBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster)
}


interface  IBarcodeHelper{
    fun add(rawBarcode: String)
    fun addAll(rawBarcodes: List<String>)
    fun remove(barcode: Barcode,group: BarcodeGroup)
}