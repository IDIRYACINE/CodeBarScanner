package com.idir.codebarscanner.ui.components

import android.app.Activity
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.idir.codebarscanner.R


@Composable
fun SecondaryAppBar(
    title : String,
){
    val context = LocalContext.current

    TopAppBar(
        title = { Text(text = title)} ,
        backgroundColor = Color.Cyan,
        navigationIcon = {
            AppBarIcon(
                appBarAction = AppBarAction.Backbutton,
                action = {
                    (context as Activity).onBackPressed()
                }
            )
        },

    )
}

@Composable
fun AppBarIcon(appBarAction: AppBarAction, action:() ->Unit ){
    IconButton(onClick = { action() }) {
        Icon(imageVector = appBarAction.icon ,contentDescription = stringResource(id = appBarAction.label))
    }
}

sealed class AppBarAction(
    @StringRes val label:Int,
    val icon:ImageVector
){
    object  Settings:AppBarAction(R.string.settings_action, Icons.Sharp.Settings)
    object Backbutton:AppBarAction(R.string.back_button , Icons.Sharp.ArrowBack)
}
