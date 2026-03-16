package com.example.noteapp.TodoFeature.HomeScreen.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Converter.Converter
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Dao.TodoDao
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
@TypeConverters(Converter::class)
@Database(entities = [TodoEntity::class], version = 2)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun TodoDao(): TodoDao

}