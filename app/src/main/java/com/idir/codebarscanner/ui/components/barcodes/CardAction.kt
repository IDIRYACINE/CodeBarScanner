package com.idir.codebarscanner.ui.components.barcodes

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.data.VoidCallback
import androidx.compose.ui.graphics.Color
import com.idir.codebarscanner.data.ToggleCallback
import com.idir.codebarscanner.ui.theme.Green200
import com.idir.codebarscanner.ui.theme.Green500


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
        colors = SwitchDefaults.colors(
            checkedThumbColor = Green500,
            checkedTrackColor = Green200
        ),
        onCheckedChange = {
            isActive.value = it
            onClick(it)
        }
    )

}
