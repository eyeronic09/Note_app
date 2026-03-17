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
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

enum class Prioritise{
    Low , Medium , High
}
data class TodoAddScreenUiState (
    val title: String = "",
    val description: String = "",
    val date: LocalDateTime? = null,
    val priority: Prioritise = Prioritise.Low,
    val time: LocalDateTime? = null,
    val error : Boolean = false
)

sealed interface onEventTodoAdd {
    data class Title(val title: String) : onEventTodoAdd
    data class Description(val description: String) : onEventTodoAdd
    data class SetPriority(val priority: Prioritise) : onEventTodoAdd
    data class SetDate(val date: Long?) : onEventTodoAdd


    object AddTodo : onEventTodoAdd
}
class TodoAddScreenVM (val repository: TodoRepository) : ViewModel() {
    private val _UiState = MutableStateFlow(TodoAddScreenUiState())
    val UiState: StateFlow<TodoAddScreenUiState> = _UiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(onEvent: onEventTodoAdd) {
        when(onEvent){
            is onEventTodoAdd.AddTodo -> {
                if (_UiState.value.title.isNotEmpty()){
                    insertTodo()
                }
            }

            is onEventTodoAdd.Description -> {
                _UiState.update {
                    it.copy(description = onEvent.description)
                }
            }
            is onEventTodoAdd.SetDate -> {
                _UiState.update {
                    it.copy(
                        date = onEvent.date?.let { millis ->
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(millis),
                                ZoneId.systemDefault()
                            )
                        }
                    )
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
            val today = LocalDate.now()
            val todo = Todo(
                title = _UiState.value.title,
                description = _UiState.value.description,
                date = (_UiState.value.date?.toLocalDate() ?: today),
                time = _UiState.value.time,
                priority = _UiState.value.priority.name,
                isCompleted = false
            )
            repository.insertAndUpdateTodo(todo)

        }

    }

}