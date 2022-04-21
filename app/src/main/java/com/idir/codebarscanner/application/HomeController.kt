package com.idir.codebarscanner.application

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.CardPopupState
import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.screens.BarcodeGroupContentActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class HomeController : ViewModel() {

    var barcodes : SnapshotStateList<BarcodeGroup>

    private var editableGroup : BarcodeGroup? = null

    private lateinit var contentActivityGroup : BarcodeGroup

    lateinit var popupCardState : CardPopupState
        private set


    init {
        barcodes = mutableStateListOf(
            BarcodeGroup(
                mutableStateOf("Test"), mutableStateListOf(
                    Barcode("","")
                ),
                mutableStateOf(false)
            )
        )
        popupCardState = CardPopupState(
            open = false,
            onConfirmCreate = {
                addGroup(it)
                popupCardState.isOpen.value = false
            },
            onConfirmEdit = {
                editGroup(it)
                popupCardState.isOpen.value = false
            },
            onCancel = {popupCardState.isOpen.value = false }
        )
    }


    private fun addGroup(name:String){
        barcodes.add(
            BarcodeGroup(
                mutableStateOf(name), mutableStateListOf(), mutableStateOf(false)
            )
        )
    }

    fun toggleGroup(group: BarcodeGroup){
        if(editableGroup != null){
            editableGroup!!.isActive.value = false
        }

        group.isActive.value = true
        editableGroup = group
    }

    fun deleteGroup(group:BarcodeGroup){
        barcodes.remove(group)
    }

    private fun editGroup(newName:String){
      editableGroup!!.name.value = newName
    }

    fun sendData(){

    }

    fun setEditGroup(group: BarcodeGroup) {
        editableGroup = group
    }

    fun startGroupContentActivity(context: Context,group: BarcodeGroup){
        contentActivityGroup = group
        context.startActivity(Intent(context, BarcodeGroupContentActivity::class.java))
    }

    fun load(context : Context){
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_groups)
        barcodes = try {
            Provider.storageManager.loadFromFile(directory)
        } catch (exception:Exception){
            mutableStateListOf()
        }
    }

    fun save(context: Context){
        val json = Json.encodeToString(barcodes)
        val directory = context.filesDir.absolutePath +'/'+ context.getString(R.string.file_groups)
        Provider.storageManager.saveToFile(json , directory)
    }

}