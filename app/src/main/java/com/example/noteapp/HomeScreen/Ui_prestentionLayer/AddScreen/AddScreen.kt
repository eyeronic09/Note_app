package com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen

import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenUIState
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.RichNoteEditor
import org.koin.compose.viewmodel.koinViewModel

class _AddScreen() : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        AddScreenRoute()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AddScreenRoute(viewModel: HomeScreenViewModel = koinViewModel()) {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val onEvent = viewModel::onEvent

        AddScreen(
            state = state,
            onAction = onEvent,
            modifier = Modifier,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddScreen(
        state: HomeScreenUIState,
        onAction: (HomeScreenEvent) -> Unit,
        modifier: Modifier
    ) {
        val context = LocalContext.current
        val pickMultipleMedia = rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia()
        ) { uris ->
            if (uris.isNotEmpty()) {
                uris.forEach { uri ->
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
                onAction(HomeScreenEvent.OnImageSelected(uris))
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        val navigator = LocalNavigator.current
                        IconButton(
                            onClick = {
                                navigator?.popAll()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "navigation"
                            )
                        }
                    },
                    title = {
                        Text("Compose Note")
                    },
                    actions = {
                        val navigator = LocalNavigator.current
                        IconButton(
                            onClick = {
                                onAction(HomeScreenEvent.AddNote)
                                navigator?.pop()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.NoteAdd,
                                contentDescription = "Add_Notes"
                            )
                        }
                        IconButton(
                            onClick = {
                                pickMultipleMedia.launch(
                                    input = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.InsertPhoto,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { paddingValues: PaddingValues ->
            AddScreenContent(
                state = state,
                onAction = onAction,
                modifier = modifier.padding(paddingValues)
            )
        }
    }

    @Composable
    fun AddScreenContent(
        state: HomeScreenUIState,
        onAction: (HomeScreenEvent) -> Unit,
        modifier: Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (state.noteEditor.imageUri.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.noteEditor.imageUri) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.noteEditor.title,
                onValueChange = { newText ->
                    onAction(HomeScreenEvent.UpdateTitle(title = newText))
                },
                label = { Text("Title") },
                singleLine = true
            )

            RichNoteEditor(
                state = state.copy(
                    noteEditor = state.noteEditor.copy(isWriting = true)
                ),
                onAction = onAction,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}
