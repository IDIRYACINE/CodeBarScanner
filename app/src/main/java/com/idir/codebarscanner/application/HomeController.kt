package com.idir.codebarscanner.application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.BarcodeGroupEntry
import com.idir.codebarscanner.data.CardPopupState
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeManager
import com.idir.codebarscanner.infrastructure.services.ServiceBroadcaster
import com.idir.codebarscanner.ui.screens.BarcodeGroupContentActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class HomeController(private val barcodesManager: IBarcodeManager) {

    lateinit var popupCardState : CardPopupState
        private set

    init {
        popupCardState = CardPopupState(
            open = false,
            onConfirmCreate = {
                addGroup(it)
                popupCardState.isOpen.value = false
            },
            onConfirmEdit = {
                barcodesManager.editGroup(it)
                popupCardState.isOpen.value = false
            },
            onCancel = {popupCardState.isOpen.value = false }
        )
    }

    private fun addGroup(name:String){
       barcodesManager.addGroup(name)
    }

    fun deleteGroup(group:BarcodeGroup){
        barcodesManager.removeGroup(group)
    }

    fun startGroupContentActivity(context: Context,group: BarcodeGroup){
        barcodesManager.setActiveGroup(group)
        context.startActivity(Intent(context, BarcodeGroupContentActivity::class.java))
    }

    fun setEditGroup(group:BarcodeGroup){
        barcodesManager.setActiveGroup(group)
    }

    fun sendData(handler: Handler){
        Provider.httpManager.sendData(handler)

    }

    fun toggleGroup(group: BarcodeGroup,value:Boolean) {
        barcodesManager.toggleGroup(group,value)
    }


}