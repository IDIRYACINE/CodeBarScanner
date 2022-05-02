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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End

        ) {

            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_url),
                initialValue = settings.host.value,
                onValueChange = {settings.host.value = it}
            )
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_username),
                initialValue = settings.username.value,
                onValueChange = {
                    settings.username.value = it
                }
            )
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_password),
                initialValue = settings.password.value,
                onValueChange = {settings.password.value = it}
            )

        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodeBarScannerTheme {
        SettingsScreen()
    }
}

