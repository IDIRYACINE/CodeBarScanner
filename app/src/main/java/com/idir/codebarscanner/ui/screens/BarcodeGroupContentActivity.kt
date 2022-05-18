package com.idir.codebarscanner.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.data.ActionsIcons
import com.idir.codebarscanner.data.Barcode
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.Provider
import com.idir.codebarscanner.infrastructure.barcode.IBarcodeManager
import com.idir.codebarscanner.ui.components.barcodes.BarcodeCard
import com.idir.codebarscanner.ui.components.SecondaryAppBar
import com.idir.codebarscanner.ui.theme.Green500

class BarcodeGroupContentActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodeGroupContentScreen(Provider.barcodesManager)
        }
    }
}

@Composable
fun BarcodeGroupContentScreen(barcodesManager: IBarcodeManager){
    val barcodeGroup: BarcodeGroup = barcodesManager.getActiveGroup()

    Scaffold(
        topBar = { SecondaryAppBar(title = barcodeGroup.name.value)})
    {
        LazyColumn(){

            barcodeGroup.barcodes.forEach{
                    barcode -> item{BarcodeCard(barcode = barcode.value)}
            }

        }
    }
}