package com.example.noteapp.TodoFeature.HomeScreen.UiLayer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val todo: List<Todo> = emptyList(),
    val isLoading : Boolean = false
)
sealed interface TodoEvent {
    data class OnToggleCompleted(val todo: Todo) : TodoEvent
    data class UpdateTodo(val todo: Todo) : TodoEvent
    data class DeleteTodo(val todo: Todo) : TodoEvent
}
class HomeScreenVM(
    private val repository: TodoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.DeleteTodo -> {}
            is TodoEvent.OnToggleCompleted -> {}
            is TodoEvent.UpdateTodo -> {}
        }
    }

    init {
        getAllTodos()
    }

    private fun getAllTodos() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = false)
            }
            try {
                val todo = repository.getTodos().collectLatest {
                    _uiState.update { state ->
                        state.copy(todo = it)
                    }
                }
                Log.d("todos", "getAllTodos: ${todo})")
            } catch (e: Exception) {
                Log.d("todos", "getAllTodos: ${e.message}")
            }


        }
    }
}