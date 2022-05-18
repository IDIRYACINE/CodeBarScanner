package com.idir.codebarscanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupPositionProvider
import com.idir.codebarscanner.R
import com.idir.codebarscanner.application.HomeController
import com.idir.codebarscanner.data.BarcodeGroup
import com.idir.codebarscanner.infrastructure.Provider


@Composable
fun ActionsDrop(controller:HomeController){
        fun hideActionsDrop(){
            controller.visibleActions.value = false
        }
    Card(
        elevation = 5.dp,
        modifier = Modifier.padding(15.dp)
    ){
        Column{
        IconButton(
            modifier = Modifier.background(Color.White),
            onClick = {
                controller.popupCardState.setCreateState()
                controller.showPopupCard()
                hideActionsDrop()
            }
        ) {
            Text(Provider.resourceLoader.loadStringResource(R.string.button_new))
        }
        IconButton(
            modifier = Modifier.background(Color.White),
            onClick = {
                controller.clearAll()
                hideActionsDrop()
            }
        ) {
            Text(Provider.resourceLoader.loadStringResource(R.string.button_clear_all))
        }
        IconButton(
            modifier = Modifier.background(Color.White),
            onClick = {
                controller.sendData()
                hideActionsDrop()
            }
        ) {
            Text(Provider.resourceLoader.loadStringResource(R.string.button_send))
        }
    }
    }

}

@Composable
fun ActionsGroupDrop(controller: HomeController,barcodeGroup: BarcodeGroup){
    fun hideActionsDrop(){
        controller.visibleGroupAction.value = false
    }

        DropdownMenuItem(
            onClick = {
                controller.setEditGroup(barcodeGroup)
                controller.popupCardState.setEditState()
                hideActionsDrop()
                controller.popupCardState.value = barcodeGroup.name
                controller.popupCardState.isOpen.value = true
            }
        ){
            Text(Provider.resourceLoader.loadStringResource(R.string.actions_edit))
        }
            DropdownMenuItem(
            onClick = {
                controller.clearGroup(barcodeGroup)
                hideActionsDrop()
            }
        ){
            Text(Provider.resourceLoader.loadStringResource(R.string.button_clear_all))
        }
            DropdownMenuItem(
            onClick = {
                controller.deleteGroup(barcodeGroup)
                hideActionsDrop()
            }
        ){
            Text(Provider.resourceLoader.loadStringResource(R.string.actions_delete))
        }


}

// The following is edited from Compose Material DropdownMenuPositonProvider
internal val MenuVerticalMargin = 48.dp

@Immutable
internal data class DropupMenuPositionProvider(
    val contentOffset: DpOffset,
    val density: Density,
    val onPositionCalculated: (IntRect, IntRect) -> Unit = { _, _ -> }
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        // The min margin above and below the menu, relative to the screen.
        val verticalMargin = with(density) { MenuVerticalMargin.roundToPx() }
        // The content offset specified using the dropdown offset parameter.
        val contentOffsetX = with(density) { contentOffset.x.roundToPx() }
        val contentOffsetY = with(density) { contentOffset.y.roundToPx() }

        // Compute horizontal position.
        val toRight = anchorBounds.left + contentOffsetX
        val toLeft = anchorBounds.right - contentOffsetX - popupContentSize.width
        val toDisplayRight = windowSize.width - popupContentSize.width
        val toDisplayLeft = 0
        val x = if (layoutDirection == LayoutDirection.Ltr) {
            sequenceOf(
                toRight,
                toLeft,
                // If the anchor gets outside of the window on the left, we want to position
                // toDisplayLeft for proximity to the anchor. Otherwise, toDisplayRight.
                if (anchorBounds.left >= 0) toDisplayRight else toDisplayLeft
            )
        } else {
            sequenceOf(
                toLeft,
                toRight,
                // If the anchor gets outside of the window on the right, we want to position
                // toDisplayRight for proximity to the anchor. Otherwise, toDisplayLeft.
                if (anchorBounds.right <= windowSize.width) toDisplayLeft else toDisplayRight
            )
        }.firstOrNull {
            it >= 0 && it + popupContentSize.width <= windowSize.width
        } ?: toLeft

        // Compute vertical position.
        val toBottom = maxOf(anchorBounds.bottom + contentOffsetY, verticalMargin)
        val toTop = anchorBounds.top - contentOffsetY - popupContentSize.height
        val toCenter = anchorBounds.top - popupContentSize.height / 2
        val toDisplayBottom = windowSize.height - popupContentSize.height - verticalMargin
        val y = sequenceOf(toBottom, toTop, toCenter, toDisplayBottom).firstOrNull {
            it >= verticalMargin &&
                    it + popupContentSize.height <= windowSize.height - verticalMargin
        } ?: toTop

        onPositionCalculated(
            anchorBounds,
            IntRect(x, y, x + popupContentSize.width, y + popupContentSize.height)
        )
        return IntOffset(x, y)
    }
}

