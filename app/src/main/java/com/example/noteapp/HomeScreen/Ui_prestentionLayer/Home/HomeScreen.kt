@file:Suppress("DEPRECATION")

package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen._AddScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen._ViewAndEditScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.DefaultAppBar
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.NoteCard
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.SearchAppBar
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.emptyNotes
import org.koin.androidx.compose.koinViewModel
import com.example.noteapp.R

object NoteTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Notes",
            icon = painterResource(R.drawable.outline_notes_24)
        )

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        Navigator(_HomeScreen())
    }
}

class _HomeScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        HomeScreenRoute()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenRoute(viewModel: HomeScreenViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel::onEvent

    HomeScreen(
        state,
        onAction = event,
    )
}

@Composable
fun HomeScreen(
    state: HomeScreenUIState,
    onAction: (HomeScreenEvent) -> Unit
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                if (state.isSearching) {
                    SearchAppBar(
                        state.searchedText,
                        onValueChange = { onAction(HomeScreenEvent.OnSearchQueryChanged(it)) },
                        onCloseClick = { onAction(HomeScreenEvent.CloseSearch) }
                    )
                } else {
                    DefaultAppBar(
                        onSearchClicked = { onAction(HomeScreenEvent.TapToSearch) }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navigator?.push(_AddScreen()) }
                ) {
                    Icon(Icons.Default.Add, "add note")
                }
            }
        ) { innerPadding ->
            HomeScreenContent(
                state = state,
                modifier = Modifier.padding(innerPadding),
                onAction = onAction,
                navigator = navigator,
                context = context
            )
        }
    }

}

@Composable
fun HomeScreenContent(
    state: HomeScreenUIState,
    modifier: Modifier,
    onAction: (HomeScreenEvent) -> Unit,
    navigator: Navigator?,
    context: Context
) {
    when {
        state.notes.isEmpty() -> {
            Box(modifier = modifier.fillMaxSize()) {
                emptyNotes()
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(state.notes) { notes ->
                    NoteCard(
                        note = notes,
                        onNoteClick = {
                            Log.d(
                                "Navigation",
                                "Pushing Screen. Current stack size: ${navigator?.items?.size} and ${notes.id}"
                            )
                            navigator?.push(item = _ViewAndEditScreen(notes.id))
                            Log.d(
                                "Navigation",
                                "Push successful. New last item: ${navigator?.lastItem}"
                            )
                        },
                        onClickDelete = {
                            onAction(HomeScreenEvent.DeleteNote(notes))
                        },
                        onShare = {
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Title : ${notes.title}  Content: ${notes.content}"
                                )
                            }
                            val chooser = Intent.createChooser(sendIntent, "share using")
                            context.startActivity(chooser)
                        }
                    )
                }
            }
        }
    }
}
