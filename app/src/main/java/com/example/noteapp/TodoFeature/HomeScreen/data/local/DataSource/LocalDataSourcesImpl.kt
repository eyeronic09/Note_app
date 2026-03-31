package com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao.TodoDao
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LocalDataSourcesImpl(private val dao: TodoDao) : LocalDataSources {
    override fun getTodos(): Flow<List<TodoEntity>> {
        return dao.getTodos()
    }

    override suspend fun insertTodo(todoEntity: TodoEntity) {
        return dao.insertTodo(todoEntity)
    }

    override suspend fun updateTodo(todoEntity: TodoEntity) {
        return dao.updateTodo(todoEntity)
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        return dao.deleteTodo(todoEntity)
    }

    override suspend fun getSpecificTodoFromDate(date: LocalDate): Flow<List<TodoEntity>> {
        return  dao.getSpecificTodoFromDate(date)
    }

    override suspend fun getTodoById(id: Int): TodoEntity? {
        return dao.getTodoById(id)
    }

}
