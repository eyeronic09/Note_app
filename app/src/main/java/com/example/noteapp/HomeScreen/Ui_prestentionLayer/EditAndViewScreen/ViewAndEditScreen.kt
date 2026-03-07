package com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenUIState
import org.koin.androidx.compose.koinViewModel

class _ViewAndEditScreen(val noteId: Int) : Screen {
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
        LaunchedEffect(noteId) {
            event(
                HomeScreenEvent.OpenToReadAndUpdate(
                    noteId = noteId
                )
            )
        }
        NoteScreen(
            modifier = Modifier.padding(),
            state = state,
            onAction = event
        )


    }

    @Composable
    fun NoteScreen(
        modifier: Modifier,
        state: HomeScreenUIState,
        onAction: (HomeScreenEvent) -> Unit
    ) {
        val navigator = LocalNavigator.current
        Scaffold() { innerPadding ->
            NoteScreenContent(
                state = state,
                modifier = modifier.padding(innerPadding),
                onAction = onAction
            )
        }
    }

    @Composable
    fun NoteScreenContent(
        state: HomeScreenUIState,
        modifier: Modifier,
        onAction: (HomeScreenEvent) -> Unit
    ) {
        when {
            state.currentNoteId != -1 -> {
                val themePrimaryColor = MaterialTheme.colorScheme.primary
                val textContestColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                    Spacer(modifier = Modifier.height(16.dp))
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
    }
}
