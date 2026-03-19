package com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao.TodoDao
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

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

    override suspend fun getSpecificTodoFromDate(date: LocalDate): Flow<List<TodoEntity>> {
        return  dao.getSpecificTodoFromDate(date)
    }
}