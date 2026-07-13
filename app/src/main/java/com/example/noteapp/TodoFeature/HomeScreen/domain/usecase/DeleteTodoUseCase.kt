package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import com.example.noteapp.TodoFeature.Todo_Notification.Model.NotificationContent
import com.example.noteapp.TodoFeature.Todo_Notification.NotificationDataSource.NotificationActions
import com.example.noteapp.TodoFeature.Todo_Notification.Scheduler.NotificationScheduler
import java.time.LocalDateTime

class DeleteTodoUseCase(private val repository: TodoRepository ,private val notificationActions: NotificationActions) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
        notificationActions.cancel(NotificationContent(
            triggerTime = LocalDateTime.of(todo.date , todo.time),
            todoMessage = todo.title
        ))
    }
}
