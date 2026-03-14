package com.example.noteapp.TodoFeature.AddScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.koin.androidx.compose.koinViewModel

class _TodoAddScreen(): Screen {
    @Composable
    override fun Content() {
        AddTodoScreenMain()
    }

    @Composable
    fun AddTodoScreenMain(viewModel : TodoAddScreenVM = koinViewModel()) {
        val state by viewModel.UiState.collectAsState()
        val event = viewModel::onEvent
    }

}