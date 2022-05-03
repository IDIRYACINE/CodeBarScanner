package com.idir.codebarscanner.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.idir.codebarscanner.R
import com.idir.codebarscanner.application.SettingsController
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.AttributeRow
import com.idir.codebarscanner.ui.theme.CodeBarScannerTheme

class SettingsActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SettingsScreen()
        }
    }

}

@Composable
fun SettingsScreen(controller : SettingsController = Provider.settingsController){
    val settings = controller.settings

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        GeneralSection(settings = settings)
        ScanControlsSection(settings = settings)
        AboutSection(settings = settings)
    }
}

fun selfToggle(state: MutableState<Boolean>){
    state.value = !state.value
}

@Composable
fun SelfSwitch(state: MutableState<Boolean>){
    Switch(checked = state.value, enabled = false, onCheckedChange = {selfToggle(state)})
}

@Composable
fun GeneralSection(settings: Settings){
    SettingSectionHeader(title = R.string.settings_section_general)

    SettingRow(
        title = R.string.settings_host_url,
        onClick = {},
        actionComposable = {},
    )

    SettingRow(
        title = R.string.settings_host_password,
        onClick = {},
        actionComposable = {},
    )

    SettingRow(
        title = R.string.settings_host_username,
        onClick = {},
        actionComposable = {},
    )

    Divider(color= Color.LightGray, thickness = 1.dp)

}

@Composable
fun ScanControlsSection(settings: Settings){
    Divider(color= Color.LightGray, thickness = 1.dp)
    SettingSectionHeader(title = R.string.settings_section_ScanControls)

    SettingRow(
        title = R.string.settings_vibration,
        icon = SettingsIcons.Vibrate,
        onClick = {selfToggle(settings.vibrate)},
        actionComposable = { SelfSwitch(state = settings.vibrate) },
    )

    SettingRow(
        title = R.string.settings_playSound,
        icon = SettingsIcons.PlaySound,
        onClick = {selfToggle(settings.playSound)},
        actionComposable = { SelfSwitch(state = settings.playSound) },
    )

    SettingRow(
        title = R.string.settings_scanContinuous,
        description = R.string.settings_scanContinuous_description,
        icon = SettingsIcons.ContinuousScan,
        onClick = {selfToggle(settings.continuousScan)},
        actionComposable = { SelfSwitch(state = settings.continuousScan) },
    )

    SettingRow(
        title = R.string.settings_scanManually,
        description = R.string.settings_scanManually_description,
        icon = SettingsIcons.ManualScan,
        onClick = {selfToggle(settings.manualScan)},
        actionComposable = { SelfSwitch(state = settings.manualScan) },
    )

    SettingRow(
        title = R.string.settings_scanDuplicate,
        description = R.string.settings_scanDuplicate_description,
        icon = SettingsIcons.DuplicateScan,
        onClick = {selfToggle(settings.duplicateScan)},
        actionComposable = { SelfSwitch(state = settings.duplicateScan) },
    )
    Divider(color= Color.LightGray, thickness = 1.dp)
}

@Composable
fun AboutSection(settings: Settings){}

