package com.example.noteapp.TodoFeature.HomeScreen.UiLayer

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

data class HomeScreenUiState(
    val todo: List<Todo> = emptyList(),
    val todayDate : LocalDate,
    val isLoading : Boolean = false
)

sealed interface TodoHomeScreenEvent {
    data class OnToggleCompleted(val todo: Todo) : TodoHomeScreenEvent
    data class UpdateTodoHomeScreen(val todo: Todo) : TodoHomeScreenEvent
    data class DeleteTodoHomeScreen(val todo: Todo) : TodoHomeScreenEvent
}

@RequiresApi(Build.VERSION_CODES.O)
class TodoHomeScreenVM(
    private val repository: TodoRepository
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState = MutableStateFlow(HomeScreenUiState(
        todayDate = LocalDate.now() ,
    ))
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun onTodoEvent(event: TodoHomeScreenEvent) {
        when (event) {
            is TodoHomeScreenEvent.DeleteTodoHomeScreen -> {}
            is TodoHomeScreenEvent.OnToggleCompleted -> {}
            is TodoHomeScreenEvent.UpdateTodoHomeScreen -> {}
        }
    }

    init {
        getAllTodos()
    }

    @RequiresApi(Build.VERSION_CODES.O)
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