package com.example.noteapp.TodoFeature.AddScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

enum class Prioritise{
    Low , Medium , High
}
data class TodoAddScreenUiState(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val priority: Prioritise = Prioritise.Low,
    val deadlineTimestamp: String? = "",
    val error : Boolean = false
)

sealed interface onEventTodoAdd {
    data class Title(val title: String) : onEventTodoAdd
    data class Description(val description: String) : onEventTodoAdd
    data class SetPriority(val priority: Prioritise) : onEventTodoAdd
    data class SetDeadline(val deadline: String) : onEventTodoAdd


    object AddTodo : onEventTodoAdd
}
class TodoAddScreenVM (val repository: TodoRepository) : ViewModel() {
    private val _UiState = MutableStateFlow(TodoAddScreenUiState())
    val UiState: StateFlow<TodoAddScreenUiState> = _UiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(onEvent: onEventTodoAdd) {
        when(onEvent){
            is onEventTodoAdd.AddTodo -> {
                insertTodo()
            }

            is onEventTodoAdd.Description -> {
                _UiState.update {
                    it.copy(description = onEvent.description)
                }
            }
            is onEventTodoAdd.SetDeadline -> {
                _UiState.update {
                    it.copy(deadlineTimestamp = onEvent.deadline)
                }
            }
            is onEventTodoAdd.SetPriority -> {
                _UiState.update {
                    it.copy(priority = onEvent.priority)
                }
            }
            is onEventTodoAdd.Title -> _UiState.update {
                it.copy(title = onEvent.title)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTodo() {
        viewModelScope.launch {
            val currentDateAndTime = LocalDateTime.now()
            val todo = Todo(
                title = _UiState.value.title,
                description = _UiState.value.description,
                date = currentDateAndTime,
                deadlineTimestamp = _UiState.value.deadlineTimestamp,
                priority = _UiState.value.priority.name,
                isCompleted = false
            )

            repository.insertAndUpdateTodo(todo)

        }

    }

}