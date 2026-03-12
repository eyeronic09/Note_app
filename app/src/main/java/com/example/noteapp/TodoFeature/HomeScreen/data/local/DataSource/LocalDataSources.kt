package com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSources {
    suspend fun getTodos() : Flow<List<TodoEntity>>
    fun insertAndUpdateTodo (todoEntity: TodoEntity)
    fun deleteTodo (todoEntity: TodoEntity)
}