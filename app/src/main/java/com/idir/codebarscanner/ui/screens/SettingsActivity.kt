package com.idir.codebarscanner.ui.screens

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.R
import com.idir.codebarscanner.application.SettingsController
import com.idir.codebarscanner.ui.components.AttributeRow
import com.idir.codebarscanner.ui.components.SecondaryAppBar
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
fun SettingsScreen(controller : SettingsController = SettingsController()){
    var state by remember {mutableStateOf(controller.settings)}


        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End

        ) {

            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_url),
                initialValue = state.host,
                onValueChange = {state.host = it}
            )
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_username),
                initialValue = state.username,
                onValueChange = {
                    state = state.copy(state.host,it,state.password)
                    Log.wtf("IDIRIDIR",controller.settings.username)
                }
            )
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_password),
                initialValue = state.password,
                onValueChange = {state.password = it}
            )
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    controller.save(context)
                    (context as Activity).onBackPressed()
                }
            ) {
                Text(stringResource(id = R.string.save_button))
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodeBarScannerTheme {
        SettingsScreen()
    }
}

