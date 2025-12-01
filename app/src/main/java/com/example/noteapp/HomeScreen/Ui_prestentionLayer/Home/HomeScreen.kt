@file:Suppress("DEPRECATION")

package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.NoteCard
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.emptyNotes
import com.example.noteapp.HomeScreen.coreScreen.SealedScreen
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel(), navController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel::onEvent

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes App") },
                actions = {
                    IconButton(
                        onClick = { event(HomeScreenEvent.LoadNotes) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(SealedScreen.AddScreen.route)
                }
            ) {
                Text("+")
                IconButton(
                    onClick = {
                        navController.navigate(SealedScreen.AddScreen.route)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
                }
            }
        }
    ) { innerPadding ->
        if (uiState.notes.isEmpty()) emptyNotes()
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = innerPadding) {

            items(uiState.notes) { note ->
                NoteCard(
                    note = note,
                    onNoteClick = {
                        navController.navigate(
                            SealedScreen.View_add_Edit.createRoute(noteId = note.id),

                        )
                    },
                    onClickDelete = {
                       event(HomeScreenEvent.DeleteNote(note))
                    },
                    onShare = {
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT,
                                 "${note.title}  + {${note.content}}"
                                )
                        }
                        val chooser = Intent.createChooser(sendIntent , "share using")
                        context.startActivity(chooser)
                    }
                )
            }
        }
    }

}


