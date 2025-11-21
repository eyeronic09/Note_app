package com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.coreScreen.SealedScreen
import com.example.noteapp.HomeScreen.domain_layer.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAndEditScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    noteId: Int,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel::onEvent

    // This will run when the noteId changes
    LaunchedEffect(noteId) {
        if (noteId != -1) {
            event(HomeScreenEvent.OpenToReadAndUpdate(noteId = noteId))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Reading Mode")
                },
                actions = {
                    IconButton(onClick = {
                        event(HomeScreenEvent.UpdateNote)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(SealedScreen.HomeScreen.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )
    { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.title,
                onValueChange = { newText ->
                    event(HomeScreenEvent.UpdateTitle(title = newText))
                },
                label = { Text("Title") },
                singleLine = true
            )


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                value = uiState.content,
                onValueChange = { newText ->
                    event(HomeScreenEvent.UpdateContent(content = newText))
                },
                label = { Text("Content") },
                maxLines = Int.MAX_VALUE
            )
        }
    }
}