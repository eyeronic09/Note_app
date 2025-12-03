package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun BasicDropdownMenu(
    onClickDelete:() -> Unit,
    onShare: () -> Unit
) {
    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }
    Box(){
        IconButton(
            onClick = {isDropDownExpanded = !isDropDownExpanded}
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = isDropDownExpanded ,
            onDismissRequest = { isDropDownExpanded = false}
        ){
            DropdownMenuItem(
                text = {Text("Delete")},
                onClick = {
                    onClickDelete()
                }
            )
            DropdownMenuItem(
                text = {Text("Share")},
                onClick = {
                    onShare()
                }
            )
        }
    }
}
