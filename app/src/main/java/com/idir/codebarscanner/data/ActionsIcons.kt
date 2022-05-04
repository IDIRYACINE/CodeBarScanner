package com.idir.codebarscanner.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.sharp.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.idir.codebarscanner.R

sealed class ActionsIcons(@StringRes val label:Int, var icon:ImageVector){
    object Delete : ActionsIcons( R.string.actions_delete , Icons.Sharp.Delete )
    object Add : ActionsIcons(R.string.actions_add , Icons.Filled.AddCircle)
    object Send : ActionsIcons(R.string.actions_send , Icons.Sharp.Send)
    object Edit : ActionsIcons(R.string.actions_edit,Icons.Sharp.Edit)
}


sealed class SettingsIcons(var icon: ImageVector){
    object Vibrate : SettingsIcons(Icons.Sharp.Delete)
    object PlaySound : SettingsIcons(Icons.Sharp.Delete)
    object ContinuousScan : SettingsIcons(Icons.Sharp.Delete)
    object ManualScan : SettingsIcons(Icons.Sharp.Delete)
    object DuplicateScan : SettingsIcons(Icons.Sharp.Delete)
}

sealed class CameraIcons(var icon: ImageVector){
    object Flash : CameraIcons(Icons.Sharp.Info)
    object Analyse : CameraIcons(Icons.Filled.AccountCircle)
}
