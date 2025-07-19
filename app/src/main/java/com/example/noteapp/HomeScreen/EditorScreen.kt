package com.example.noteapp.HomeScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditorScreen(navController: NavController, noteId: Int, mainViewModel: MainViewModel) {
    val notes by mainViewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId }

    var title by remember { mutableStateOf(note?.title ?:"") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var date by remember { mutableStateOf(note?.date ?: "") }


    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(
            onClick = {
                if (noteId == 0) {
                    mainViewModel.addNote(title = title, description = content , date = date)
                }
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Save")
        }
     }
}