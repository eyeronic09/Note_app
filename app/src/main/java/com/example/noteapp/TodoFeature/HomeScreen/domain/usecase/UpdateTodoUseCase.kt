package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository

class UpdateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo)
    }
}
