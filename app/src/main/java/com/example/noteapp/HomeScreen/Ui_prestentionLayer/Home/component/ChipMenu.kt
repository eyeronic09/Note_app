package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MenuRows(onOldestClick:() -> Unit) {

    Row {

        Chip(chipText = "Oldest" , onClick = onOldestClick)
    }
}
@Composable
private fun Chip(
    chipText : String,
    onClick:() -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = 4.dp) // Adds space between chips
    ) {
        Text(
            text = chipText,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.small
                )
                .padding(
                    horizontal = 16.dp, vertical = 8.dp
                )
                .clickable(
                    onClick = onClick
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun menuRow() {
    MenuRows({})
}