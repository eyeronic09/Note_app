package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllTodosUseCase(
    private val todoRepository: TodoRepository
){
    operator fun invoke() : Flow<List<Todo>>  {
        return todoRepository.getTodos()
    }
}