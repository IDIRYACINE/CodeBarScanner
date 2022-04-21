package com.idir.codebarscanner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.idir.codebarscanner.ui.theme.CodeBarScannerTheme

@Composable
fun AttributeRow(
    attributeName: String,
    initialValue: String,
    onValueChange: (textFieldValue:String) -> Unit,
    space: Int? = 10,
    padding: Int? = 16
){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(padding!!.dp)
    ) {
      CustomLabel(attributeName)
      Spacer(modifier = Modifier.width(space!!.dp))
      CustomTextField(initialValue , onValueChange)
    }
}

@Composable
private fun CustomLabel(name : String){
    Text(name)
}

@Composable
private fun CustomTextField(value:String ,onValueChange: (textFieldValue : String) -> Unit){
    TextField(value = value, onValueChange = onValueChange)

}



