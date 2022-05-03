package com.idir.codebarscanner.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.sharp.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.idir.codebarscanner.R

sealed class ActionsIcons(@StringRes val label:Int, var icon:ImageVector){
    object Delete : ActionsIcons( R.string.actions_delete , Icons.Sharp.Delete )
    object Add : ActionsIcons(R.string.actions_add , Icons.Filled.AddCircle)
    object ActiveCard : ActionsIcons(R.string.actions_active,Icons.Sharp.KeyboardArrowDown)
    object DesactiveCard : ActionsIcons(R.string.actions_desactive,Icons.Sharp.KeyboardArrowUp)
    object Send : ActionsIcons(R.string.actions_send , Icons.Sharp.Send)
    object Edit : ActionsIcons(R.string.actions_edit,Icons.Sharp.Edit)
}


sealed class SettingsIcons(@StringRes val label:Int, var icon:ImageVector){
    object Vibrate : SettingsIcons( R.string.actions_delete , Icons.Sharp.Delete )
    object PlaySound : SettingsIcons( R.string.actions_delete , Icons.Sharp.Delete )
    object ContinuousScan : SettingsIcons( R.string.actions_delete , Icons.Sharp.Delete )
    object ManualScan : SettingsIcons( R.string.actions_delete , Icons.Sharp.Delete )
    object DuplicateScan : SettingsIcons( R.string.actions_delete , Icons.Sharp.Delete )
}
