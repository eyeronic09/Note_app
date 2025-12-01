package com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.coreScreen.SealedScreen
import android.util.Log
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAndEditScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    noteId: Int,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel::onEvent



    LaunchedEffect(noteId) {
        if (noteId != -1) {
            event(HomeScreenEvent.OpenToReadAndUpdate(noteId = noteId))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (uiState.isWriting) {
                        Text("Writing Mode")
                    } else {
                        Text("Reading Mode")
                    }
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
                    IconButton(
                        onClick = {
                            event(HomeScreenEvent.SetToEdit)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
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
    { paddingValues ->
        val themePrimaryColor = MaterialTheme.colorScheme.primary
        val textContestColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                value = uiState.title,
                enabled = uiState.isWriting,
                onValueChange = { updatedContent ->
                    event(HomeScreenEvent.UpdateTitle(title = updatedContent))
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
                value = uiState.content,
                enabled = uiState.isWriting,
                onValueChange = { updatedTitle ->
                    event(HomeScreenEvent.UpdateContent(content = updatedTitle))
                },
                label = { Text("Content") },
                maxLines = Int.MAX_VALUE
            )
        }
    }
}
