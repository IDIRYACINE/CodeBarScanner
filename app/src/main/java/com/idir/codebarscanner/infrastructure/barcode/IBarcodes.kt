package com.idir.codebarscanner.infrastructure.barcode

import androidx.camera.core.ImageAnalysis
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.barcode.commands.ICommand
import java.util.*

interface ICameraAnalyser{
    fun setBarcodeAnalyser(analyser:ImageAnalysis.Analyzer,processor:IBarcodeAnalyser)
    fun getBarcodeAnalyser() : ImageAnalysis
    fun toggleActiveState()
    fun getActiveState() : MutableState<Boolean>
    fun setCamera(cameraId:Int)
    fun getCamera(): State<Int>

}

interface IBarcodeAnalysisMode{
    fun isAllowed() : Boolean
    fun turnOf()
    fun turnOn()
    fun onAnalysisSuccess()
}

interface IBarcodeAnalyser{
    fun setManualMode()
    fun setContinuousMode()
    fun toggleActiveState()
    fun getActiveState() : MutableState<Boolean>
}

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
    fun clearAll()
    fun clearActiveGroups()
    fun clearGroup(group:BarcodeGroup)
}

abstract class IBarcodeGroupHelper{
    abstract fun add(groupName: String)

    abstract fun remove(group: BarcodeGroup)

    fun timeStamp(): String {
        return "${Calendar.DAY_OF_MONTH}/${Calendar.MONTH}/${Calendar.YEAR} ${Calendar.HOUR_OF_DAY}:${Calendar.MINUTE}:${Calendar.SECOND} "
    }

    abstract fun subscribeToBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster)
    abstract fun unsubscribeFromBarcodeBroadcaster(broadcaster: IBarcodeBroadcaster)
    abstract fun setBarcodeHelper(helper: IBarcodeHelper)
}


abstract class IBarcodeHelper{
    abstract fun add(rawBarcode: String)
    abstract fun addAll(rawBarcodes: List<String>)

    abstract fun remove(
        barcode: Barcode,group: BarcodeGroup,iterator: MutableIterator<MutableMap.MutableEntry<String, Barcode>>)

    fun getEntryKey(rawBarcode: String, groupId: String) : String{
        return "${groupId}${rawBarcode}"
    }

    fun timeStamp(): String {
        return System.currentTimeMillis().toString()
    }
    fun currentTime() : String{
        val time = Calendar.getInstance()
        val day = "${time.get(Calendar.DAY_OF_WEEK)}/${time.get(Calendar.DAY_OF_MONTH)}/${time.get(Calendar.YEAR)}"
        val hour= "${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}:${time.get(Calendar.SECOND)}"

        return "$day $hour"
    }
}