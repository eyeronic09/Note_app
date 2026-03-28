package com.example.noteapp.TodoFeature.HomeScreen.UiLayer

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import com.kizitonwose.calendar.core.WeekDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class HomeScreenUiState(
    val todo: List<Todo> = emptyList(),
    val completedTodos: List<Todo> = emptyList(),
    val todayDate : LocalDate,
    val selectedDate : String = "",
    val isLoading : Boolean = false,

    )

sealed interface TodoHomeScreenEvent {

    data class OnSpecificDate(val date : WeekDay): TodoHomeScreenEvent
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
        todayDate = LocalDate.now()
    ))
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun onTodoEvent(event: TodoHomeScreenEvent) {
        when (event) {
            is TodoHomeScreenEvent.DeleteTodoHomeScreen -> {}
            is TodoHomeScreenEvent.OnToggleCompleted -> {
                markCompleted(event.todo)
            }
            is TodoHomeScreenEvent.UpdateTodoHomeScreen -> {

            }
            is TodoHomeScreenEvent.OnSpecificDate -> {
                    _uiState.update {
                        it.copy(selectedDate = _uiState.value.selectedDate )
                    }
                    getSpecificTodoFromDate(event)

            }
        }
    }
    private fun markCompleted(todo: Todo) {
        viewModelScope.launch {
            try {
                val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)
                repository.insertAndUpdateTodo(updatedTodo)

            } catch (e: Exception) {
                Log.d("todos", "Error marking todo as completed: ${e.message}")
            }
        }
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

    private fun getSpecificTodoFromDate(event: TodoHomeScreenEvent.OnSpecificDate) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = false, todo = emptyList())
            }
            try {
                val selectedDate = event.date.date // this date is iso formated
                val completedTodos = _uiState.value.todo.filter { it ->
                    it.isCompleted
                }
                val todo = repository.getSpecificTodoFromDate(selectedDate).collectLatest { todo ->
                    _uiState.update { state ->
                        state.copy(
                            todo = todo,
                            completedTodos = completedTodos
                        )
                    }
                }


            } catch (e: Exception) {

                Log.d("todos", "getAllTodos: ${e.message}")
            }

        }
    }
}