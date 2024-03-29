package com.idir.codebarscanner.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.idir.codebarscanner.App
import com.idir.codebarscanner.R
import com.idir.codebarscanner.application.SettingsController
import com.idir.codebarscanner.data.Settings
import com.idir.codebarscanner.data.SettingsIcons
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.SettingRow
import com.idir.codebarscanner.ui.components.SettingSectionHeader
import com.idir.codebarscanner.ui.components.barcodes.ManageCardPopup
import com.idir.codebarscanner.ui.theme.Green200
import com.idir.codebarscanner.ui.theme.Green500

object SettingsScreenConstants{
    val dividerThickness  = 1.dp
    const val sectionTitleTopPadding = 10
}

@Composable
fun SettingsScreen(controller : SettingsController = Provider.settingsController){
    val settings = controller.settings
    val openDialog = remember{ controller.popupCardState.isOpen}

    if(openDialog.value){
        ManageCardPopup(state = controller.popupCardState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        GeneralSection(settings = settings,controller = controller)
        ScanControlsSection(settings = settings,controller = controller)
        AboutSection(controller = controller)
    }
}

@Composable
fun SelfSwitch(state: MutableState<Boolean>,onClick : ()->Unit = {}){
    Switch(checked = state.value,
        colors = SwitchDefaults.colors(
        checkedThumbColor = Green500,
        checkedTrackColor = Green200
    ), onCheckedChange = { onClick() })
}

@Composable
fun GeneralSection(settings: Settings,controller: SettingsController){
    SettingSectionHeader(title = R.string.settings_section_general,padding=SettingsScreenConstants.sectionTitleTopPadding)

    SettingRow(
        title = controller.loadStringResource(R.string.settings_host_url),
        description = settings.host.value,
        onClick = {controller.showEditPopup(settings.host,R.string.popup_host)},
        actionComposable = {},
    )

    SettingRow(
        title = controller.loadStringResource(R.string.settings_host_username),
        description = settings.username.value,
        onClick = {controller.showEditPopup(settings.username,R.string.popup_username)},
        actionComposable = {},
    )

    SettingRow(
        title = controller.loadStringResource(R.string.settings_host_password),
        description = settings.password.value,
        onClick = { controller.showEditPopup( settings.password,R.string.popup_password)},
        actionComposable = {},
    )

    Divider(color= Color.LightGray, thickness = SettingsScreenConstants.dividerThickness)

}

@Composable
fun ScanControlsSection(settings: Settings,controller:SettingsController){
    Divider(color= Color.LightGray, thickness =SettingsScreenConstants.dividerThickness)
    SettingSectionHeader(title = R.string.settings_section_scanControls,padding=SettingsScreenConstants.sectionTitleTopPadding)

    SettingRow(
        title = controller.loadStringResource(R.string.settings_vibration),
        icon = SettingsIcons.Vibrate,
        onClick = {controller.toggleVibration()},
        actionComposable = { SelfSwitch(state = settings.vibrate, onClick = { controller.toggleVibration() }) },
    )

    SettingRow(
        title = controller.loadStringResource(R.string.settings_playSound),
        icon = SettingsIcons.PlaySound,
        onClick = {controller.togglePlaySound()},
        actionComposable = { SelfSwitch(state = settings.playSound, onClick = {controller.togglePlaySound()}) },
    )

    SettingRow(
        title = controller.loadStringResource(R.string.settings_scanManually),
        description = controller.loadStringResource(R.string.settings_scanManually_description),
        icon = SettingsIcons.ManualScan,
        onClick = {controller.toggleManualScan()},
        actionComposable = { SelfSwitch(state = settings.manualScan,onClick = {controller.toggleManualScan()}) },
    )

    SettingRow(
        title = controller.loadStringResource(R.string.settings_scanDuplicateGroup),
        description = controller.loadStringResource(R.string.settings_scanDuplicateGroup_description),
        icon = SettingsIcons.DuplicateGroup,
        onClick = {controller.toggleDuplicateBarcodeGroups()},
        actionComposable = { SelfSwitch(state = settings.duplicateGroup, onClick = {controller.toggleDuplicateBarcodeGroups()}) },
    )

    SettingRow(
        title = controller.loadStringResource(R.string.settings_scanDuplicate),
        description = controller.loadStringResource(R.string.settings_scanDuplicate_description),
        icon = SettingsIcons.DuplicateBarcode,
        onClick = {controller.toggleDuplicateBarcodeScan()},
        actionComposable = { SelfSwitch(state = settings.duplicateScan, onClick = {controller.toggleDuplicateBarcodeScan()}) },
    )


    SettingRow(
        title = controller.loadStringResource(R.string.settings_clearSend),
        description = controller.loadStringResource(R.string.settings_clearSend_description),
        icon = SettingsIcons.Clear,
        onClick = {controller.toggleClearSend()},
        actionComposable = { SelfSwitch(state = settings.clearSend, onClick = {controller.toggleClearSend()}) },
    )


    Divider(color= Color.LightGray, thickness = SettingsScreenConstants.dividerThickness)
}

@Composable
fun AboutSection(controller:SettingsController){
    val context =  App.appInstance.applicationContext
    Divider(color= Color.LightGray, thickness = SettingsScreenConstants.dividerThickness)
    SettingSectionHeader(title = R.string.settings_section_about, padding = SettingsScreenConstants.sectionTitleTopPadding)

    SettingRow(
        title = controller.loadStringResource(R.string.settings_version),
        description = controller.getAppVersion(),
        onClick = {
            controller.showOnPlayStore(context)

        },
        actionComposable = {},
    )
}
