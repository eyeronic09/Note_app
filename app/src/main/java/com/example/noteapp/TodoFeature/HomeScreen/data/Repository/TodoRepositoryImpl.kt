package com.example.noteapp.TodoFeature.HomeScreen.data.Repository

import android.util.Log
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSources
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Mapper.toDomain
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Mapper.toEntity
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TodoRepositoryImpl(
    private val localDataSources: LocalDataSources
) : TodoRepository {
    override fun getTodos(): Flow<List<Todo>> {
        return localDataSources.getTodos().map {
            it.map { todoEntity ->
                todoEntity.toDomain()
            }
        }
    }

    override suspend fun insertAndUpdateTodo(todo: Todo) {
        return localDataSources.insertAndUpdateTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: Todo) {
        return localDataSources.deleteTodo(todo.toEntity())
    }

    override suspend fun getSpecificTodoFromDate(date: LocalDate): Flow<List<Todo>> {
        return localDataSources.getSpecificTodoFromDate(
            date = date
        ).map { it ->
            it.map { todoEntity -> todoEntity.toDomain() }

        }.also {
            Log.d("getDate", "$it")
        }

    }


}