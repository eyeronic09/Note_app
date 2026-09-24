package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.sign_in.domain.model.UserData
import com.example.noteapp.ui.theme.NoteAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    value: String,
    onValueChange: (String) -> Unit ,
    onCloseClick : () -> Unit
) {
    TopAppBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        navigationIcon = {},
        title = {
            TextField(
                value = value,
                onValueChange = {onValueChange(it)},
                placeholder = {Text("Search Notes")},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        },
        actions = {
            IconButton(onClick = onCloseClick ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Search"
                )
            }
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    userdata: UserData? = null,
    onSearchClicked: () -> Unit,
    onSortClicked: () -> Unit,
    onSideBar: () -> Unit
) {
    TopAppBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = {
            val titleText = if (!userdata?.username.isNullOrBlank()) "${userdata.username} 's  Notes" else "offline notes"
            Text(text = titleText)
        },
        actions = {
            IconButton(onClick = onSortClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Sort,
                    contentDescription = "Sort"
                )
            }
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Notes"
                )
            }

        },
        navigationIcon = {
            IconButton(onClick = onSideBar)
             {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultAppBarPreview() {
    NoteAppTheme {
        DefaultAppBar(
            userdata = UserData(userId = "1", username = "John"),
            onSearchClicked = {},
            onSortClicked = {},
            onSideBar = {}
        )
    }
}


