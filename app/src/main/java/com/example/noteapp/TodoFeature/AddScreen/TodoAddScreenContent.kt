package com.example.noteapp.TodoFeature.AddScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
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

        TodoAddScreen(state = state , onAction = event)


    }

    @Composable
    fun TodoAddScreen(state : TodoAddScreenUiState , onAction:(onEventTodoAdd) -> Unit){
        val navigator = LocalNavigator.current
        Box(modifier = Modifier.fillMaxSize()){
            Scaffold() {  it ->
                TodoAddScreenContent(
                    state = state,
                    modifier = Modifier.padding(it),
                    onAction = onAction,
                    navigator = navigator
                )
            }
        }

    }
}

@Composable
fun TodoAddScreenContent(
    state : TodoAddScreenUiState,
    modifier: Modifier,
    onAction: (onEventTodoAdd) -> Unit,
    navigator: Navigator?,
){
    when {
        (state.error) -> {
            Text("error")
        }
        else ->{
            TodoAddScreen(
                state = state,
                onAction = onAction,
                modifier = modifier
            )
        }
    }
}

