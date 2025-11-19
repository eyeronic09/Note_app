package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component.NoteCard
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (viewModel: HomeScreenViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel::onEvent

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Notes App")}
            )
        },
        floatingActionButton = {}
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.fillMaxSize() , contentPadding = innerPadding) {
            items(uiState.notes){
                NoteCard(it)
            }
        }
    }
}


