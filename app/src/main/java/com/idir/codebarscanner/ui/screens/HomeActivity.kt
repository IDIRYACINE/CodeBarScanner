package com.idir.codebarscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.ActionsDrop
import com.idir.codebarscanner.ui.components.barcodes.BarcodeGroupCard
import com.idir.codebarscanner.ui.components.barcodes.ManageCardPopup
import com.idir.codebarscanner.ui.theme.Green500


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(controller : HomeController = Provider.homeController ){
    val openDialog = remember{ controller.popupCardState.isOpen}

    Scaffold(
        floatingActionButton = {
            Column {
                if(controller.visibleActions.value)
                {
                   ActionsDrop(controller = controller)
                }
            Spacer(modifier = Modifier.height(5.dp))
            IconButton(
                modifier = Modifier.padding(end = 25.dp,start =25.dp),
                onClick = {
                    controller.toggleActions()
                }
            ){
                Icon(imageVector = ActionsIcons.Add,
                    contentDescription = null,
                    tint = Green500,
                    modifier = Modifier.scale(2.5f)
                    )
            }

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

            items(Provider.barcodesManager.getGroups()){
                barcodeGroup -> BarcodeGroupCard(barcodeGroup,controller)    }
            }
        }
    }
