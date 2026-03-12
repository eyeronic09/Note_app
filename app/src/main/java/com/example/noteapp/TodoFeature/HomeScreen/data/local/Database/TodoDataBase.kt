package com.example.noteapp.TodoFeature.HomeScreen.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao.TodoDao
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun TodoDao(): TodoDao

}