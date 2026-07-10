package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetTodosByDateUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(date: LocalDate): Flow<List<Todo>> {
        return repository.getSpecificTodoFromDate(date)
    }
}
