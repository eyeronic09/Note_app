package com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface LocalDataSources {
    fun getTodos() : Flow<List<TodoEntity>>
    suspend fun insertAndUpdateTodo (todoEntity: TodoEntity)
    suspend fun deleteTodo (todoEntity: TodoEntity)
    suspend fun getSpecificTodoFromDate(date : LocalDate) : Flow<List<TodoEntity>>

    suspend fun getTodoById(id: Int) : TodoEntity?
}