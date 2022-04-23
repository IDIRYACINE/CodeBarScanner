package com.idir.codebarscanner.ui.components.barcodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.codebar.BarcodeBroadcaster
import com.idir.codebarscanner.infrastructure.codebar.IBarcodeBroadcaster


@Composable
fun BarcodeGroupCard(barcodeGroup : BarcodeGroup, controller:HomeController , barcodeBroadcaster: IBarcodeBroadcaster){
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                controller.startGroupContentActivity(context,barcodeGroup)
            },
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Start
        ) {

            Text(text = barcodeGroup.name.value)

            Row(
                horizontalArrangement = Arrangement.End
            ) {
              CardAction(action = ActionsIcons.Edit, onClick = {
                  controller.setEditGroup(barcodeGroup)
                  controller.popupCardState.isOpen.value = true
                  controller.popupCardState.setEditState()
              })
              CardAction(action = ActionsIcons.Delete, onClick = {controller.deleteGroup(barcodeGroup)})
              ToggleAction(
                  active = barcodeGroup.isActive,
                  onClick = {
                      barcodeGroup.isActive.value = it
                      if(it){
                          barcodeBroadcaster.subscribeToBarcodeStream(barcodeGroup)
                      }else{
                          barcodeBroadcaster.unsubscribeFromBarcodeStream(barcodeGroup)
                      }
                  }
              )}
            }
        }
    }