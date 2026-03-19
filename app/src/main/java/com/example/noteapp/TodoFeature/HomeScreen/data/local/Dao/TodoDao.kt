package com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TodoDao {
    @Query("Select * From todo")
    fun getTodos(): Flow<List<TodoEntity>>

    @Upsert
    suspend fun insertAndUpdateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM todo WHERE date = :date")
    fun getSpecificTodoFromDate(date: LocalDate): Flow<List<TodoEntity>>
}
