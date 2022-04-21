package com.idir.codebarscanner.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.barcodes.BarcodeCard
import com.idir.codebarscanner.ui.components.SecondaryAppBar

class BarcodeGroupContentActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodeGroupContentScreen(Provider.homeController.barcodes[0])
        }
    }
}

@Composable
fun BarcodeGroupContentScreen(barcodeGroup: BarcodeGroup){
    Scaffold(
        topBar = { SecondaryAppBar(title = barcodeGroup.name.value)}
    ) {
        LazyColumn(){
            items(barcodeGroup.barcodes){
                barcode -> BarcodeCard(barcode = barcode)
            }
        }
    }
}