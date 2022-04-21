package com.idir.codebarscanner.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.barcodes.BarcodeGroupCard
import com.idir.codebarscanner.ui.components.barcodes.ManageCardPopup

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(controller : HomeController = Provider.homeController ){

    val openDialog = remember{ controller.popupCardState.isOpen}

    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = {
                    controller.popupCardState.setCreateState()
                    openDialog.value = true
                }
            ){
                val action = ActionsIcons.Add
                Icon(imageVector = action.icon, contentDescription = stringResource(id = action.label) )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        if(openDialog.value){
            ManageCardPopup(controller.popupCardState)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(controller.barcodes){
                barcodeGroup -> BarcodeGroupCard(barcodeGroup,controller)
            }
        }
    }
}