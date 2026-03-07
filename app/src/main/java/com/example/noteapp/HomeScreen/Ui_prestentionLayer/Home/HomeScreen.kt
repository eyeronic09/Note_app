@file:Suppress("DEPRECATION")

package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.DefaultAppBar
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.NoteCard
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.SearchAppBar
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.emptyNotes
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
//fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel(), navController: NavController) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val onEvent = viewModel::onEvent
//
//    val context = LocalContext.current
//
//    Scaffold(
//        topBar = {
//            if (uiState.isSearching){
//                SearchAppBar(
//                    value = uiState.searchedText,
//                    onValueChange = {onEvent(HomeScreenEvent.OnSearchQueryChanged(query = it))},
//                    onCloseClick = {onEvent(HomeScreenEvent.CloseSearch)}
//                )
//            }else {
//                DefaultAppBar(
//                    onSearchClicked = { onEvent(HomeScreenEvent.TapToSearch) }
//                )
//            }
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    navController.navigate(SealedScreen.AddScreen.route)
//                }
//            ) {
//                IconButton(
//                    onClick = {
//                        navController.navigate(SealedScreen.AddScreen.route)
//                    }
//                ) {
//                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
//                }
//            }
//        }
//    ) { innerPadding ->
//        if (uiState.notes.isEmpty()) emptyNotes()
//        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = innerPadding) {
//            item {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    OldestChip(
//                        onClickToggle = {onEvent(HomeScreenEvent.Oldest)},
//                        onSelected = uiState.isOldest,
//                        onUnSelect = {onEvent(HomeScreenEvent.OnUnSelectOldest)}
//                    )
//                    NewestChip(
//                        onClickToggle = {onEvent(HomeScreenEvent.Newest)},
//                        onSelected = uiState.isNewest,
//                        onUnSelect = {onEvent(HomeScreenEvent.OnUnSelectNewest)}
//                    )
//                }
//
//            }
//
//            items(uiState.notes) { note ->
//                NoteCard(
//                    note = note,
//                    onNoteClick = {
//                        navController.navigate(
//                            SealedScreen.View_add_Edit.createRoute(noteId = note.id),
//                        )
//                    },
//                    onClickDelete = {
//                       onEvent(HomeScreenEvent.DeleteNote(note))
//                    },
//                    onShare = {
//                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
//                            type = "text/plain"
//                            putExtra(Intent.EXTRA_TEXT,
//                                 "Title : ${note.title}  Content: ${note.content}"
//                                )
//                        }
//                        val chooser = Intent.createChooser(sendIntent , "share using")
//                        context.startActivity(chooser)
//                    }
//                )
//            }
//        }
//    }
//}
//

class _HomeScreen () : Screen {
    @Composable
    override fun Content() {
        HomeScreenRoute()
    }
    @Composable
    fun HomeScreenRoute(viewModel: HomeScreenViewModel = koinViewModel()){
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val event = viewModel::onEvent

        HomeScreen(
            state,
            onAction = event,
        )
    }

    @Composable
    fun HomeScreen(
        state : HomeScreenUIState,
        onAction : (HomeScreenEvent) -> Unit
    ){
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                if (state.isSearching){
                    SearchAppBar(
                        value = state.searchedText,
                        onValueChange = {onAction(HomeScreenEvent.OnSearchQueryChanged(query = it))},
                        onCloseClick = {onAction(HomeScreenEvent.CloseSearch)}
                    )
                }else {
                    DefaultAppBar(
                        onSearchClicked = { onAction(HomeScreenEvent.TapToSearch) }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {

                    }
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
                    }
                }
            }
        ) { innerPadding ->
            HomeScreenContent(
                state = state,
                modifier = Modifier.padding(innerPadding),
                onAction = onAction
            )
        }
    }
    @Composable
    fun HomeScreenContent(
        state: HomeScreenUIState,
        modifier: Modifier,
        onAction: (HomeScreenEvent) -> Unit
    ) {
        val local = LocalContext.current
        when {
            state.notes.isEmpty() -> {
                emptyNotes()
            }
            else -> {
                LazyColumn(
                    modifier = modifier.fillMaxSize()
                ) {
                    items(state.notes) { notes ->
                        NoteCard(
                            note = notes,
                            onNoteClick = {
                                onAction(HomeScreenEvent.OpenToReadAndUpdate(notes.id))
                            },
                            onClickDelete = {
                                onAction(HomeScreenEvent.DeleteNote(notes))
                            },
                            onShare = {
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT,
                                        "Title : ${notes.title}  Content: ${notes.content}"
                                    )
                                }
                                val chooser = Intent.createChooser(sendIntent, "share using")
                                local.startActivity(chooser)
                            }
                        )
                    }
                }

            }
        }
    }
}