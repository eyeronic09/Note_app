package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.noteapp.TodoFeature.AddScreen.todoCreationEvent
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import java.time.LocalDate
import java.time.LocalTime

class AddTodoUseCase(private val todoRepository: TodoRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        title : String,
        description: String,
        date : LocalDate ,
        time : LocalTime,
        priority : String,
        isCompleted : Boolean = false
    ) {
        val today = LocalDate.now()
        val todo = Todo(
            title = title,
            description = description,
            date = date,
            time = time,
            priority = priority,
            isCompleted = isCompleted
        )
        return todoRepository.insertTodo(todo)
    }
}