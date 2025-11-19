package com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Handle navigation after successful note addition
    LaunchedEffect(Unit) {
        if (uiState.value.isLoading) return@LaunchedEffect
        
        uiState.value.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Error: $error",
                    withDismissAction = true
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.value.title,
                onValueChange = { viewModel.changeTitle(it)},
                label = { Text("Title") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                value = uiState.value.content,
                onValueChange = { viewModel.changeContent(it) },
                label = { Text("content") },
                maxLines = Integer.MAX_VALUE,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        TODO()
                    }
                },
                enabled = !uiState.value.isLoading &&
                         uiState.value.title.isNotBlank() &&
                         uiState.value.content.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.value.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Save Note")
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showSystemUi = true)
@Composable
fun AddScreenPreview() {
    MaterialTheme {
        // AddScreen(navController = rememberNavController())
        // Note: Preview might not work properly with Hilt ViewModel
        // You can create a preview version with a fake ViewModel if needed
    }
}
