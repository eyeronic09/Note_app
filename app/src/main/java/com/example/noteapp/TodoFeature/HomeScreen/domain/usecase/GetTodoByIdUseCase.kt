package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository

class GetTodoByIdUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): Todo {
        return repository.getSpecificTodo(id)
    }
}
