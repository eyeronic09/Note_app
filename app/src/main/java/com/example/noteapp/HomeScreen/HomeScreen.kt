package com.example.noteapp.HomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp.ui.theme.NoteAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Notes App")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val noteViewModel: MainViewModel = viewModel()
            val noteList by noteViewModel.notes.collectAsState(initial = emptyList())
            val dummyNotes = listOf(
                Note(1, "First Note", "This is the first dummy note."),
                Note(2, "Second Note", "This is the second dummy note."),
                Note(3, "Third Note", "This is the third dummy note.")
            )
            LazyColumn {
                items(noteList) { note ->
                    Card(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()) {
                        Text(
                            text = note.title,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )

                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NoteAppTheme {
        HomeScreen()
    }
}