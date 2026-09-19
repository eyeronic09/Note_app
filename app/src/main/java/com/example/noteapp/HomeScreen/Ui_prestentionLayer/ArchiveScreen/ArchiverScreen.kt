package com.example.noteapp.HomeScreen.Ui_prestentionLayer.ArchiveScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenContent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.NoteCard
import org.koin.compose.viewmodel.koinViewModel

class _ArchiverScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        ArchiverScreenRoute()
    }

}
@Composable
fun ArchiverScreenRoute(viewModel : ArchiverScreenViewModel = koinViewModel() , ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ArchiverScreen(uiState = uiState, onEvent = viewModel::onEvent)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiverScreen(
    uiState : ArchiverStateUiState,
    onEvent: (ArchiverEvent) -> Unit
){
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Archive") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        ArchiverScreenContent(uiState, onEvent , Modifier.padding(innerPadding))
    }
}

@Composable
fun ArchiverScreenContent( uiState: ArchiverStateUiState, onEvent: (ArchiverEvent) -> Unit ,modifier: Modifier ){
    when (uiState) {
        is ArchiverStateUiState.Content -> {
            LazyColumn(modifier.fillMaxSize()) {
                items(uiState.listOfNote){ note ->
                    NoteCard(
                        note = note,
                        onArchiver = { onEvent(ArchiverEvent.unArchiver(note)) }
                    )
                }
            }
        }
        is ArchiverStateUiState.Error -> {

        }
        is ArchiverStateUiState.Loading -> {
            CircularProgressIndicator()
        }
    }
}