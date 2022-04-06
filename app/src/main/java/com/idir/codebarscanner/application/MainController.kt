package com.idir.codebarscanner.application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.idir.codebarscanner.data.BarcodeGroup

data class UiAppState(
    val groupsList : List<BarcodeGroup>,
)

class MainController() : ViewModel(){

    var uiState by mutableStateOf(UiAppState(emptyList()))
        private  set


}