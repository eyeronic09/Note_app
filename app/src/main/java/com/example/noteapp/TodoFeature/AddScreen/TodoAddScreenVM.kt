package com.example.noteapp.TodoFeature.AddScreen

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
import java.util.Date

enum class Prioritise{
    Low , Medium , High
}
data class TodoAddScreenUiState(
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val priority: Prioritise = Prioritise.Low,
    val deadlineTimestamp: String? = "",
)

sealed interface onEventTodoAdd {
    data class Title(val title: String) : onEventTodoAdd
    data class Content(val content: String) : onEventTodoAdd
    data class SetPriority(val priority: Prioritise) : onEventTodoAdd
    data class SetDeadline(val deadline: String) : onEventTodoAdd

    object AddTodo : onEventTodoAdd
}
class TodoAddScreenVM (val repository: TodoRepository) : ViewModel() {
    private val _UiState = MutableStateFlow(TodoAddScreenUiState())
    val UiState: StateFlow<TodoAddScreenUiState> = _UiState.asStateFlow()

    fun onEvent(onEvent: onEventTodoAdd) {
        when(onEvent){
            is onEventTodoAdd.AddTodo -> {
                insertTodo()
            }

            is onEventTodoAdd.Content -> {
                _UiState.update {
                    it.copy(content = onEvent.content)
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

    fun insertTodo() {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            val todo = Todo(
                title = _UiState.value.title,
                content = _UiState.value.content,
                date = currentDate,
                deadlineTimestamp = _UiState.value.deadlineTimestamp,
                priority = _UiState.value.priority.name,
                isCompleted = false
            )

            repository.insertAndUpdateTodo(todo)

        }

    }

}