package com.idir.codebarscanner.application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.data.CardPopupState
import com.idir.codebarscanner.infrastructure.HttpManager
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.screens.BarcodeGroupContentActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class HomeController : ViewModel()  {
    var barcodes : SnapshotStateList<BarcodeGroup> = mutableStateListOf()

    private val httpManager : HttpManager

    var editableGroup : BarcodeGroup? = null
        private set

    lateinit var contentActivityGroup : BarcodeGroup
        private set

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

    fun deleteGroup(group:BarcodeGroup){
        barcodes.remove(group)
    }

    private fun editGroup(newName:String){
      editableGroup!!.name.value = newName
    }

    fun sendData(context: Context){
        val resources = context.resources
        fun onSuccess(){
            makeText(context,resources.getString(R.string.send_data_failed),Toast.LENGTH_LONG).show()
        }

        fun onFail(){
            makeText(context, resources.getString(R.string.send_data_failed),Toast.LENGTH_LONG).show()
        }

        httpManager.sendData({ onSuccess() }, { onFail() })
    }

    fun setEditGroup(group: BarcodeGroup) {
        editableGroup = group
    }

    fun startGroupContentActivity(context: Context,group: BarcodeGroup){
        contentActivityGroup = group
        context.startActivity(Intent(context, BarcodeGroupContentActivity::class.java))
    }

    fun load(context : Context) {
        val raw : List<BarcodeGroup> = BarcodeGroup.load(context)
        barcodes.addAll(raw)
    }

}