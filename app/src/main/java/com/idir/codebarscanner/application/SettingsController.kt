package com.idir.codebarscanner.application

import com.idir.codebarscanner.BuildConfig
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.idir.codebarscanner.data.CardPopupState
import com.idir.codebarscanner.data.Settings
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.ResourcesLoader
import com.idir.codebarscanner.infrastructure.barcode.*
import com.idir.codebarscanner.infrastructure.barcode.commands.ICommand
import com.idir.codebarscanner.infrastructure.barcode.commands.PlaySoundCommand
import com.idir.codebarscanner.infrastructure.barcode.commands.VibrateCommand


class SettingsController : ViewModel() {

    lateinit var settings : Settings
        private set

    lateinit var popupCardState: CardPopupState

    private lateinit var editableProperty : MutableState<String>

    private lateinit var cameraAnalyser: ICameraAnalyser

    private lateinit var googleBarcodeAnalyser :IBarcodeAnalyser

    private lateinit var barcodeManager : IBarcodeManager

    private lateinit var barcodeBroadcaster: IBarcodeBroadcaster

    private lateinit var playSoundCommand : ICommand

    private lateinit var vibrateCommand : ICommand

    private lateinit var resourceLoader : ResourcesLoader


    fun toggleDuplicateBarcodeGroups(){
        toggleBoolean(settings.duplicateGroup)
        barcodeManager.setGroupDuplicateMode(settings.duplicateGroup.value)
    }

    fun toggleDuplicateBarcodeScan(){
        toggleBoolean(settings.duplicateScan)
        barcodeManager.setBarcodeDuplicateMode(settings.duplicateScan.value)
    }

    fun toggleManualScan(){
        val value = toggleBoolean(settings.manualScan)

        if(value){
            googleBarcodeAnalyser.setManualMode()
        }
        else{
            googleBarcodeAnalyser.setContinuousMode()
        }

    }

    fun loadStringResource(resourceId:Int) : String{
        return resourceLoader.loadStringResource(resourceId)
    }

    fun togglePlaySound(){
        toggleBoolean(settings.playSound)
        if(settings.playSound.value){
            barcodeBroadcaster.registerOnNotifyCommand(playSoundCommand)
        }
        else{
            barcodeBroadcaster.unregisterOnNotifyCommand(playSoundCommand)
        }
    }

    fun toggleVibration(){
        toggleBoolean(settings.vibrate)
        if(settings.vibrate.value){
            barcodeBroadcaster.registerOnNotifyCommand(vibrateCommand)
        }
        else{
            barcodeBroadcaster.unregisterOnNotifyCommand(vibrateCommand)
        }
    }


    fun toggleClearSend(){
        toggleBoolean(settings.clearSend)
    }

    fun showEditPopup(property:MutableState<String>,title:Int){
        editableProperty = property
        popupCardState.title.value = title
        popupCardState.value.value = property.value
        popupCardState.isOpen.value = true
    }


    private fun toggleBoolean(value:MutableState<Boolean>) : Boolean{
        val newValue = !value.value
        value.value = newValue
        return newValue
    }

    fun load(context: Context) {
        settings = Provider.storageManager.loadSettings(context)

        playSoundCommand = PlaySoundCommand()
        vibrateCommand = VibrateCommand(context)

        setupInitialState()
    }

    private fun setupInitialState(){

        barcodeBroadcaster = Provider.barcodeBroadcaster

        barcodeManager = Provider.barcodesManager

        cameraAnalyser = Provider.cameraAnalyser

        resourceLoader = Provider.resourceLoader

        popupCardState = CardPopupState(
            open = false,
            onConfirmCreate = {
                editableProperty.value = it
                popupCardState.isOpen.value = false
            },
            onConfirmEdit = {
                editableProperty.value = it
                popupCardState.isOpen.value = false
            },
            onCancel = {popupCardState.isOpen.value = false }
        )

        if(settings.playSound.value){
            barcodeBroadcaster.registerOnNotifyCommand(playSoundCommand)
        }

        if(settings.vibrate.value){
            barcodeBroadcaster.registerOnNotifyCommand(vibrateCommand)
        }

        val barcodeAnalyser = GoogleBarcodeAnalyser()
        googleBarcodeAnalyser = barcodeAnalyser
        cameraAnalyser.setBarcodeAnalyser(barcodeAnalyser,barcodeAnalyser)

        if(!settings.manualScan.value){
            googleBarcodeAnalyser.setContinuousMode()
        }
        else{
            googleBarcodeAnalyser.setManualMode()
        }

    }


    fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    fun showOnPlayStore() {

    }


}