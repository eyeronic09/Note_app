package com.example.noteapp.TodoFeature.AddScreen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteapp.TodoFeature.AddScreen.compontent.MoreOptionMenu

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAddScreen(
    state: TodoAddScreenUiState,
    onAction: (todoCreationEvent) -> Unit,
    modifier: Modifier = Modifier,
    navigator : Navigator
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popUntilRoot() }
                    ) { Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow Back")}

                },
                title = { Text(if (state.isEditing) "Edit Task" else "Add Task") },
                actions = {
                    if (!state.isEditing) {
                        IconButton(
                            onClick = {
                                onAction(todoCreationEvent.AddTodo)

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save Todo"
                            )
                        }
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.InsertPhoto,
                            contentDescription = null
                        )
                    }
                }

            )
        },

    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    value = state.title,
                    onValueChange = { onAction(todoCreationEvent.Title(it)) }
                )

                OutlinedTextField(
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    value = state.description,
                    onValueChange = { onAction(todoCreationEvent.Description(it)) }
                )
                MoreOptionMenu(state , onAction)

            }

        }
    }
}
