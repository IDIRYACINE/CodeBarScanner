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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
            val controller = rememberMyAppState()
            controller.load(LocalContext.current)
            SettingsScreen(controller)
        }
    }

}

@Composable
private fun SettingsScreen(controller:SettingsController){

    Scaffold(
        topBar = {
            SecondaryAppBar(
                title = stringResource(id= R.string.settings_title),
            )
        }
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End

        ) {
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_url),
                initialValue = controller.settings.host,
                onValueChange = {controller.settings.host = it}
            )
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_username),
                initialValue = controller.settings.username,
                onValueChange = {
                    controller.settings.username = it
                    Log.wtf("IDIRIDIR",controller.settings.username)
                }
            )
            AttributeRow(
                attributeName = stringResource(id = R.string.settings_host_password),
                initialValue = controller.settings.password,
                onValueChange = {controller.settings.password = it}
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
}

@Composable
fun rememberMyAppState() = remember() {
    SettingsController()
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodeBarScannerTheme {
        SettingsScreen(SettingsController())
    }
}

