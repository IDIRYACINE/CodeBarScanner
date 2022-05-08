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
    object Send : ActionsIcons(R.string.actions_send , Icons.Sharp.Send)
    object Edit : ActionsIcons(R.string.actions_edit,Icons.Sharp.Edit)
}


sealed class SettingsIcons(var icon: Int){
    object Vibrate : SettingsIcons(R.drawable.ic_vibrate)
    object PlaySound : SettingsIcons(R.drawable.ic_sound)
    object ManualScan : SettingsIcons(R.drawable.ic_hand)
    object DuplicateBarcode : SettingsIcons(R.drawable.ic_repeat_barcode)
    object DuplicateGroup : SettingsIcons(R.drawable.ic_repeat_group)

}

sealed class CameraIcons(var icon: Int){
    object Flash : CameraIcons(R.drawable.ic_flash)
    object Analyse : CameraIcons(R.drawable.ic_recording_filled)
}
