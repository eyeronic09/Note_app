package com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao.TodoDao
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSourcesImpl(private val dao: TodoDao) : LocalDataSources {
    override fun getTodos(): Flow<List<TodoEntity>> {
        return dao.getTodos()
    }

    override suspend fun insertAndUpdateTodo(todoEntity: TodoEntity) {
        return dao.insertAndUpdateTodo(todoEntity)
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        return dao.deleteTodo(todoEntity)
    }
}