package com.idir.codebarscanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.SettingsIcons
import com.idir.codebarscanner.data.VoidCallback


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingRow(
    title:Int,
    description :Int? = null,
    icon :SettingsIcons? = null,
    onClick : VoidCallback,
    actionComposable:@Composable () -> Unit,
    proFeature : Boolean = false,
    space: Int? = 10,
    padding: Int? = 16
){
        Row(
            modifier = Modifier.clickable { onClick() }.fillMaxWidth().padding(padding!!.dp),
            horizontalArrangement = Arrangement.Center
        ){

            if (icon != null){
            Icon(imageVector = icon.icon , contentDescription = null)
            }

            Spacer(modifier = Modifier.width(space!!.dp))

            Column {
                Text(text = stringResource(id = title) ,fontWeight = FontWeight.Bold)
                if(description != null){
                    Text(text = stringResource(id = description) , fontWeight = FontWeight.ExtraLight)
                }
            }

            if(proFeature){
                ProFeatureBadget()
            }

            actionComposable()

        }

}

@Composable
fun ProFeatureBadget(){

}

@Composable
fun SettingSectionHeader(title:Int){
    Text(text = stringResource(id = title),
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.settings_section))
}