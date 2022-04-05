package com.idir.codebarscanner.ui.components

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.idir.codebarscanner.R

@Composable
fun AppBar(){
    TopAppBar(
        title = { Text(text = stringResource(id= R.string.app_name))} ,
        backgroundColor = Color.Cyan,
        actions = {
            AppBarIcon(appBarAction = AppBarAction.Settings , action = {

            })
        }
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
}
