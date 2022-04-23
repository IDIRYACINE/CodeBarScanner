package com.idir.codebarscanner.ui.components.barcodes

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.data.VoidCallback
import androidx.compose.material.Switch
import com.idir.codebarscanner.data.ToggleCallback


@Composable
fun CardAction(action: ActionsIcons,
               onClick:VoidCallback){
    IconButton(onClick = { onClick() }) {
        Icon(imageVector = action.icon, contentDescription = stringResource(action.label))
    }
}

@Composable
fun ToggleAction(
                 onClick:ToggleCallback,
                 active : MutableState<Boolean>
                 ){

    val isActive = remember {active}
    Switch(
        checked = isActive.value,
        onCheckedChange = {
            isActive.value = it
            onClick(it)
        }
    )

}