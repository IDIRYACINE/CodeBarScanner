package com.idir.codebarscanner.ui.components.barcodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.data.Barcode


@Composable
fun BarcodeCard(barcode: Barcode){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = barcode.value)
        }
    }
}