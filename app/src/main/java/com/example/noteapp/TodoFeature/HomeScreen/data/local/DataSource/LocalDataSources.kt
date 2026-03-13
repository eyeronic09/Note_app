package com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSources {
    fun getTodos() : Flow<List<TodoEntity>>
    suspend fun insertAndUpdateTodo (todoEntity: TodoEntity)
    suspend fun deleteTodo (todoEntity: TodoEntity)
}