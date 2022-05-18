package com.idir.codebarscanner.ui.components

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.idir.codebarscanner.R
import com.idir.codebarscanner.ui.theme.Green500


@Composable
fun SecondaryAppBar(
    title : String,
){
    val context = LocalContext.current

    TopAppBar(
        title = { Text(text = title , color = Color.White)} ,
        backgroundColor = Green500,
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
        Icon(imageVector = appBarAction.icon ,contentDescription = stringResource(id = appBarAction.label),tint=Color.White)
    }
}

sealed class AppBarAction(
    @StringRes val label:Int,
    val icon:ImageVector
){
    object  Settings:AppBarAction(R.string.settings_action, Icons.Sharp.Settings)
    object Backbutton:AppBarAction(R.string.button_back , Icons.Sharp.ArrowBack)
}

@Composable
fun AdBanner(){
    val isInEditMode = LocalInspectionMode.current
    TopAppBar(
        backgroundColor = Green500,
        ) {
        if (!isInEditMode) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->

                    AdView(context).apply {
                        adSize = AdSize.BANNER
                        adUnitId = context.getString(R.string.ad_id_banner)
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )}
    }




}
