package com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("Select * From todo")
    suspend fun getTodos(): Flow<List<TodoEntity>>

    @Upsert
     fun insertAndUpdateTodo(todo: TodoEntity)

    @Delete
     fun deleteTodo(todo: TodoEntity)


}