package com.example.noteapp.HomeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Make sure this import is present
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// REMOVE: import androidx.lifecycle.viewmodel.compose.viewModel // No longer creating viewModel here

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, mainViewModel: MainViewModel) { // Use the mainViewModel passed as a parameter
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Navigate to EditorScreen for a new note (ID 0)
                navController.navigate("${Screen.EditorScreen.route}/0")
            }) {
                Icon(Icons.Filled.Add, "Add new note")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Use the mainViewModel instance that was passed in
            val noteList by mainViewModel.notes.collectAsState()

            if (noteList.isEmpty()) {
                Text(
                    text = "No notes yet. Click the '+' button to add one!",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(
                        items = noteList
                    ) { note ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .fillMaxWidth()
                                .clickable(onClick = {
                                    navController.navigate("${Screen.EditorScreen.route}/${note.id}")
                                }),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                if (note.content.isNotBlank()) { // Only show content if it exists
                                    Text(
                                        text = note.content,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 3 // Show a preview
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
