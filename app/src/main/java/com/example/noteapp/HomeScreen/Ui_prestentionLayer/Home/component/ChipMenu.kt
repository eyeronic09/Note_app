package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OldestChip(onClickToggle:() -> Unit, onSelected: Boolean, onUnSelect:() -> Unit ) {
    FilterChip(
        modifier = Modifier.padding(horizontal = 4.dp),
        selected = onSelected,
        onClick = onClickToggle,
        leadingIcon = if (onSelected) {

            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize).clickable(enabled = true, onClick = onUnSelect)
                )
            }
        } else {
            null
        },
        label = {Text("Oldest")}
    )
}

@Composable
fun NewestChip(onClickToggle:() -> Unit, onSelected: Boolean, onUnSelect:() -> Unit ) {
    FilterChip(
        selected = onSelected,
        onClick = onClickToggle,
        leadingIcon = if (onSelected) {

            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize).clickable(enabled = true, onClick = onUnSelect)
                )
            }
        } else {
            null
        },
        label = {Text("Newest")}
    )
}

@Preview(showBackground = true)
@Composable
private fun menuRow() {
    OldestChip({}, false , {})
}