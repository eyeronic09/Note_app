package com.example.noteapp.TodoFeature.HomeScreen.domain.repository

import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun insertAndUpdateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}