package com.example.noteapp.HomeScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditorScreen(navController: NavController, noteId: Int, mainViewModel: MainViewModel) {
    val notes by mainViewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId }
    
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var date by remember { mutableStateOf(note?.date ?: "") }

    var makeReadonly by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == 0) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { if (noteId == 0) {
                            mainViewModel.addNote(title = title, description = content , date = date)
                        }
                            navController.popBackStack()

                        }
                    ) {
                        Icon(Icons.Filled.Save, "Save the note")
                    }
                    IconButton(onClick = {
                        makeReadonly =  !makeReadonly
                    }) {
                        Icon(
                            imageVector = if (makeReadonly){
                                Icons.Default.Edit
                            }else{
                                Icons.Default.RemoveRedEye},
                            contentDescription = "mode Switching"
                        )

                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {Text("Tittle")},
                readOnly = makeReadonly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                        colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
            )
            )
            TextField(
                value = content,
                placeholder = {Text("Content")},
                onValueChange = { content = it },
                readOnly = makeReadonly,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )

            )

        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditorScreenNewNotePreview() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    EditorScreen(navController = navController, noteId = 0, mainViewModel = viewModel)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditorScreenEditNotePreview() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    EditorScreen(navController = navController, noteId = 1, mainViewModel = viewModel)
}