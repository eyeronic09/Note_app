package com.example.noteapp.TodoFeature.AddScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import org.koin.compose.viewmodel.koinViewModel

class _EditScreen(val id : Int): Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        EditScreenMain(
            id = id
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun EditScreenMain(viewModel : TodoAddScreenVM = koinViewModel() , id : Int) {
        val state by viewModel.UiState.collectAsState()
        val event = viewModel::onEvent
        val navigator = LocalNavigator.currentOrThrow

        navigator?.let {
            TodoAddScreen(

                state = state,
                onAction = event,
                navigator = it,
            )
        }

    }
}