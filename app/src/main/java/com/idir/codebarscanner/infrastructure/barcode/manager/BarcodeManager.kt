package com.idir.codebarscanner.infrastructure.barcode.manager

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.StorageManager
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeGroupHelper
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeHelper
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement


class BarcodeManager : IBarcodeManager {
    private val id = "BarcodeManager"

    /* Data Structures */
    private lateinit var register:MutableMap<String,Int>
    private lateinit var barcodesRegister : MutableMap<String,Int>
    private lateinit var barcodes : MutableMap<String,BarcodeGroup>
    private lateinit var groups : SnapshotStateList<BarcodeGroup>

    private var editableGroup : BarcodeGroup? = null

    /* Actions Mode */
    private var groupHelper : IBarcodeGroupHelper? = null
    private lateinit var barcodeHelper: IBarcodeHelper
    private val activeGroups = mutableListOf<BarcodeGroup>()

    fun load(context: Context,storageManager: StorageManager) {
        val map = storageManager.loadBarcode(context)
        register = map[StorageManager.GROUPS_REGISTER_KEY] as MutableMap<String, Int>
        barcodes = map[StorageManager.GROUPS_KEY] as MutableMap<String, BarcodeGroup>
        barcodesRegister = map[StorageManager.BARCODES_REGISTER_KEY] as MutableMap<String, Int>

        val tempList :SnapshotStateList<BarcodeGroup> = mutableStateListOf()

        barcodes.forEach{
            tempList.add(BarcodeGroup(it.key,it.value.name,it.value.barcodes,it.value.isActive))
        }
        groups = tempList
    }

    override fun encodeToJson() : String{
        val map = mapOf(
            StorageManager.GROUPS_REGISTER_KEY to Json.encodeToJsonElement(register),
            StorageManager.BARCODES_REGISTER_KEY to  Json.encodeToJsonElement(barcodesRegister),
            StorageManager.GROUPS_KEY to Json.encodeToJsonElement(barcodes)
        )
        return Json.encodeToString(map)
    }

    override fun encodeActiveToJson() : String{
        val map = mapOf(
            StorageManager.GROUPS_KEY to activeGroups
        )

        return Json.encodeToString(map)
    }

    override fun addGroup(groupName: String) {
        groupHelper!!.add(groupName)
    }

    override fun editGroup( newName:String) {
        editableGroup!!.name.value = newName
    }

    override fun removeGroup(groupEntry: BarcodeGroup) {
        groupHelper!!.remove(groupEntry)
    }

    override fun setActiveGroup(groupEntry: BarcodeGroup) {
        val group = barcodes[groupEntry.id]!!
        editableGroup = group
    }

    override fun getActiveGroup() : BarcodeGroup{
        return editableGroup!!
    }

    override fun getGroups(): List<BarcodeGroup> {
        return groups
    }

    override fun setGroupDuplicateMode(value: Boolean) {
        if(groupHelper !=null){
           groupHelper!!.unsubscribeFromBarcodeBroadcaster(Provider.barcodeBroadcaster)
        }

        val helper = if(value){
            DuplicateGroupHelper(register,barcodes,groups,barcodeHelper)
        } else{
            GroupHelper(register,barcodes,groups,barcodeHelper)
        }

        groupHelper = helper
        groupHelper!!.subscribeToBarcodeBroadcaster(Provider.barcodeBroadcaster)
    }

    override fun setBarcodeDuplicateMode(value: Boolean) {
        barcodeHelper = if(value){
            DuplicateBarcodeHelper(barcodesRegister,activeGroups,editableGroup)
        } else{
            BarcodeHelper(barcodesRegister,activeGroups,editableGroup)
        }
    }

    override fun toggleGroup(group: BarcodeGroup,value: Boolean) {
        if(value){
            activeGroups.add(group)
        }else{
            activeGroups.remove(group)
        }
        group.isActive.value = value
    }

}
