package com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NoteAdd
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenUIState
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

class _AddScreen() : Screen {
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
        Scaffold (
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
                        IconButton(
                            { onAction(HomeScreenEvent.AddNote) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.NoteAdd,
                                contentDescription = "Add_Notes"
                            )
                        }
                    }
                )
            }
        ){ innerPadding ->
            AddScreenContent(
                state = state,
                onAction = onAction,
                modifier = modifier.padding(innerPadding)
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = { newText ->
                    onAction(HomeScreenEvent.UpdateTitle(title = newText))
                },
                label = { Text("Title") },
                singleLine = true
            )


            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                value = state.content,
                onValueChange = { newText ->
                    onAction(HomeScreenEvent.UpdateContent(content = newText))
                },
                label = { Text("Content") },
                maxLines = Int.MAX_VALUE
            )
        }
    }

}