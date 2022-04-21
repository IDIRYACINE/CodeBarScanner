package com.idir.codebarscanner.ui.components.barcodes

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.data.VoidCallback


@Composable
fun CardAction(action: ActionsIcons,
               onClick:VoidCallback){
    IconButton(onClick = { onClick() }) {
        Icon(imageVector = action.icon, contentDescription = stringResource(action.label))
    }
}

@Composable
fun ToggleAction(activeAction : ActionsIcons ,
                 disabledAction : ActionsIcons ,
                 onClick:VoidCallback,
                 active : MutableState<Boolean>
                 ){

    val isActive = remember {active}

    fun action() : ActionsIcons{
        if(isActive.value){
            return activeAction
        }
        else{
            return disabledAction
        }
    }

    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector =action().icon
            , contentDescription = stringResource(action().label))
    }

}