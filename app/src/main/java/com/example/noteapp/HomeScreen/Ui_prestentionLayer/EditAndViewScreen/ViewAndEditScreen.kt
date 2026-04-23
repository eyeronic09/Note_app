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
import coil.compose.AsyncImage
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenUIState
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.OpenThePhoto
import org.koin.androidx.compose.koinViewModel

class _ViewAndEditScreen(val noteId: Int) : Screen {
    override val key = "ViewAndEditScreen_$noteId"

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
        noteId: Int,
        viewModel: HomeScreenViewModel = koinViewModel(),
    ) {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val event = viewModel::onEvent
        LaunchedEffect(noteId != -1) {
            event(
                HomeScreenEvent.OpenToReadAndUpdate(
                    noteId = noteId
                )
            )
        }
        NoteScreen(
            modifier = Modifier, state = state, onAction = event
        )


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteScreen(
        modifier: Modifier, state: HomeScreenUIState, onAction: (HomeScreenEvent) -> Unit
    ) {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    when {
                        state.isWriting -> {
                            Text(text = "Writing Mode")
                        }
                        else -> {
                            Text("Reading Mode")
                        }
                    }
                }, actions = {
                    when {
                        state.isWriting -> {
                            IconButton(
                                onClick = {
                                    onAction(HomeScreenEvent.SetToEdit)
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "editing Mode"
                                )
                            }
                        }

                        else -> {

                            IconButton(
                                onClick = {
                                    onAction(HomeScreenEvent.SetToEdit)
                                }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ReadMore,
                                    contentDescription = "editing Mode"
                                )
                            }
                        }
                    }

                }, navigationIcon = {
                    IconButton(onClick = { 
                        // If we are on the root navigator, pop from there
                        // This screen was pushed to root, so navigator is root.
                        navigator?.pop() 
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                            contentDescription = "Back"
                        )
                    }
                })
            }) { innerPadding ->
            NoteScreenContent(
                state = state,
                modifier = modifier.padding(innerPadding),
                onAction = onAction, navigator = navigator
            )
        }

    }

    @Composable
    fun NoteScreenContent(
        state: HomeScreenUIState, modifier: Modifier, onAction: (HomeScreenEvent) -> Unit,
        navigator: Navigator?
    ) {

        val themePrimaryColor = MaterialTheme.colorScheme.primary
        val textContestColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        Column(
            modifier = modifier
                .fillMaxSize()
                ,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            if (state.imageUri.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.imageUri) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .clickable(
                                    onClick = {
                                        // This screen (_ViewAndEditScreen) is already on the Root Navigator.
                                        // So 'navigator' here IS the Root Navigator.
                                        // We just push to it directly.
                                        navigator?.push(
                                            item = OpenThePhoto(ImageToOpen = uri.toString())
                                        )
                                    }
                                )
                                .width(200.dp),

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
                    disabledTextColor = textContestColor,
                    focusedTextColor = textContestColor,
                    disabledLabelColor = themePrimaryColor,
                ),
                value = state.title,
                enabled = state.isWriting,
                onValueChange = { updatedContent ->
                    onAction(HomeScreenEvent.UpdateTitle(title = updatedContent))
                },
                label = { Text("Title") },
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = themePrimaryColor,
                    disabledBorderColor = themePrimaryColor,
                    disabledLabelColor = themePrimaryColor,
                    disabledTextColor = textContestColor,
                    focusedTextColor = textContestColor,
                ),
                value = state.content,
                enabled = state.isWriting,
                onValueChange = { updatedTitle ->
                    onAction(HomeScreenEvent.UpdateContent(content = updatedTitle))
                },
                label = { Text("Content") },
                maxLines = Int.MAX_VALUE
            )
        }
    }
}
