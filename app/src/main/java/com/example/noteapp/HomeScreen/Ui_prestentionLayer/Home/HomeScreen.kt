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
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen._AddScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen._ViewAndEditScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.DefaultAppBar
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.NoteCard
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.SearchAppBar
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.emptyNotes
import org.koin.androidx.compose.koinViewModel



class _HomeScreen () : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        HomeScreenRoute()
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
                        navigator?.push(_AddScreen())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add note"
                    )
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
        val navigator = LocalNavigator.current
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
                                navigator?.push(_ViewAndEditScreen(notes.id))

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