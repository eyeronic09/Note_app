package com.example.noteapp.TodoFeature.HomeScreen.domain.repository

import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun insertAndUpdateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun getSpecificTodoFromDate(todo: LocalDate) : Flow<List<Todo>>
}