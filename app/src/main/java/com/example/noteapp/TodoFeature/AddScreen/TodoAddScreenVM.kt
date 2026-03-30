package com.example.noteapp.TodoFeature.AddScreen

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

enum class Prioritise{
    Low , Medium , High
}
data class TodoAddScreenUiState (
    val id : Int? = null ,
    val title: String = "",
    val description: String = "",
    val date: LocalDate? = null,
    val priority: Prioritise = Prioritise.Low,
    val time: LocalTime? = null,
    val error : Boolean = false
)

sealed interface todoCreationEvent {
    data class  TakeTodoId(val id: Int) : todoCreationEvent
    data class Title(val title: String) : todoCreationEvent
    data class Description(val description: String) : todoCreationEvent
    data class SetPriority(val priority: Prioritise) : todoCreationEvent
    data class SetDate(val date: Long?) : todoCreationEvent
    data class SetTime(val time: LocalTime): todoCreationEvent

    object AddTodo : todoCreationEvent
}
class TodoAddScreenVM (val repository: TodoRepository) : ViewModel() {
    private val _UiState = MutableStateFlow(TodoAddScreenUiState())
    val UiState: StateFlow<TodoAddScreenUiState> = _UiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(onEvent: todoCreationEvent) {
        when(onEvent){
            is todoCreationEvent.AddTodo -> {
                if (_UiState.value.title.isNotEmpty()){
                    insertTodo()
                }
            }

            is todoCreationEvent.Description -> {
                _UiState.update {
                    it.copy(description = onEvent.description)
                }
            }
            is todoCreationEvent.SetDate -> {
                _UiState.update {
                    it.copy(
                        date = onEvent.date?.let { millis ->
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(millis),
                                ZoneId.systemDefault()
                            ).toLocalDate()
                        }
                    )
                }
            }
            is todoCreationEvent.SetPriority -> {
                _UiState.update {
                    it.copy(priority = onEvent.priority)

                }
            }
            is todoCreationEvent.Title -> _UiState.update {
                it.copy(title = onEvent.title)
            }
            is todoCreationEvent.SetTime -> {
                _UiState.update { it ->
                    it.copy(
                        time = onEvent.time
                    )

                }
            }

            is todoCreationEvent.TakeTodoId -> {
                _UiState.update {
                    it.copy(
                        id = onEvent.id,
                    )
                }
            }
        }
    }
    fun editView(){
        viewModelScope.launch {
            val todoId = _UiState.value.id
            Log.d("todoUpdate" , todoId.toString())
            if (todoId != null){
                val todo = repository.getSpecificTodo(todoId)
                Log.d("todoUpdate" , todo.toString())
                _UiState.update {
                    it.copy(
                        id = todo.id,
                        title = todo.title,
                        description = todo.description ?: "",
                        date = todo.date,
                        priority = when(todo.priority){
                            "High" -> Prioritise.High
                            "Medium" -> Prioritise.Medium
                            else -> Prioritise.Low
                        } ,
                        time = todo.time,
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTodo() {
        viewModelScope.launch {
            val today = now()
            val todo = Todo(
                title = _UiState.value.title,
                description = _UiState.value.description,
                date = (_UiState.value.date ?: today),
                time = _UiState.value.time,
                priority = _UiState.value.priority.name,
                isCompleted = false
            )
            repository.insertAndUpdateTodo(todo)

        }

    }

}
