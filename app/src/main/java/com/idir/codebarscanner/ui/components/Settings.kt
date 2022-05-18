package com.idir.codebarscanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.R
import com.idir.codebarscanner.data.VoidCallback


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingRow(
    title:String,
    description :String? = null,
    icon :Int? = null,
    onClick : VoidCallback,
    actionComposable:@Composable () -> Unit,
    proFeature : Boolean = false,
    padding: Int = 16,
){
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .padding(padding.dp),
            horizontalArrangement = Arrangement.Center
        ){

            Box(modifier = Modifier.weight(2f)) {
                if (icon != null) {
                    Icon(painter = painterResource(id = icon), contentDescription = null)
                }
            }

            Column (modifier= Modifier.weight(8f)){
                Text(text =  title )
                if(description != null){
                    Text(text =  description , fontWeight = FontWeight.ExtraLight , color = Color.LightGray)
                }
            }

            Box(modifier = Modifier.weight(2f)){
                actionComposable()
            }

        }

}


@Composable
fun SettingSectionHeader(title:Int,
    padding:Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.dp),
        horizontalArrangement = Arrangement.Start
    ){

        Box(modifier = Modifier.weight(2f)) {}

        Text(
            modifier= Modifier.weight(8f),
            text = stringResource(id = title),
            color = colorResource(id = R.color.settings_section))

        Box(modifier = Modifier.weight(2f)){}

    }

}