package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import com.example.noteapp.TodoFeature.Todo_Notification.Model.NotificationContent
import com.example.noteapp.TodoFeature.Todo_Notification.NotificationDataSource.NotificationActions
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AddTodoUseCase(private val todoRepository: TodoRepository , private val notificationAccess: NotificationActions) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        title : String,
        description: String,
        date : LocalDate ,
        time : LocalTime,
        priority : String,
        isCompleted : Boolean = false
    ) {

        val todo = Todo(
            title = title,
            description = description,
            date = date,
            time = time,
            priority = priority,
            isCompleted = isCompleted
        )
        todoRepository.insertTodo(todo)
        val notificationContent = NotificationContent(
            triggerTime = LocalDateTime.of(date, time),
            todoMessage = title
        )
        notificationAccess.scheduler(notificationContent)
    }
}