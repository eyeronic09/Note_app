package com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.ReadMore
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.AsyncImage
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenUIState
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.OpenThePhoto
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.RichNoteEditor
import org.koin.androidx.compose.koinViewModel

class _ViewAndEditScreen(val noteId: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        ViewAndEditScreenRoute(
            noteId = noteId
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ViewAndEditScreenRoute(
        noteId: String,
        viewModel: HomeScreenViewModel = koinViewModel(),
    ) {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val event = viewModel::onEvent
        LaunchedEffect(noteId) {
            if (noteId.isNotBlank()) {
                event(HomeScreenEvent.OpenToReadAndUpdate(noteId = noteId))
            }
        }

        NoteScreen(
            modifier = Modifier,
            state = state,
            onAction = event
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteScreen(
        modifier: Modifier = Modifier,
        state: HomeScreenUIState,
        onAction: (HomeScreenEvent) -> Unit
    ) {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (state.noteEditor.isWriting) {
                            Text(text = "Editing Mode")
                        } else {
                            Text(text = "Reading Mode")
                        }
                    },
                    actions = {
                        if (state.noteEditor.isWriting) {
                            IconButton(
                                onClick = {
                                    onAction(HomeScreenEvent.UpdateNote)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Save Note"
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    onAction(HomeScreenEvent.SetToEdit)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Mode"
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            NoteScreenContent(
                state = state,
                modifier = modifier.padding(innerPadding),
                onAction = onAction,
                navigator = navigator
            )
        }
    }

    @Composable
    fun NoteScreenContent(
        state: HomeScreenUIState,
        modifier: Modifier = Modifier,
        onAction: (HomeScreenEvent) -> Unit,
        navigator: Navigator?
    ) {
        val themePrimaryColor = MaterialTheme.colorScheme.primary
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {

            if (state.noteEditor.imageUri.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.noteEditor.imageUri) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .clickable {
                                    navigator?.push(OpenThePhoto(ImageToOpen = uri.toString()))
                                },
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = themePrimaryColor,
                    disabledBorderColor = themePrimaryColor,
                    disabledTextColor = textColor,
                    focusedTextColor = textColor,
                    disabledLabelColor = themePrimaryColor,
                ),
                value = state.noteEditor.title,
                enabled = state.noteEditor.isWriting,
                onValueChange = { updatedTitle ->
                    onAction(HomeScreenEvent.UpdateTitle(title = updatedTitle))
                },
                label = { Text("Title") },
                singleLine = true
            )

            RichNoteEditor(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}
