package com.idir.codebarscanner.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.ui.components.barcodes.BarcodeCard
import com.idir.codebarscanner.ui.components.SecondaryAppBar

class BarcodeGroupContentActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodeGroupContentScreen(Provider.barcodesManager.getActiveGroup())
        }
    }
}

@Composable
fun BarcodeGroupContentScreen(barcodeGroup: BarcodeGroup){

    Scaffold(
        topBar = { SecondaryAppBar(title = barcodeGroup.name.value)}
    ) {
        LazyColumn(){

            barcodeGroup.barcodes.forEach{
                    barcode -> item{BarcodeCard(barcode = barcode.value)}
            }

        }
    }
}