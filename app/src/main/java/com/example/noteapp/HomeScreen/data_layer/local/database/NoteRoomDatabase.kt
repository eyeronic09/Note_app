package com.example.noteapp.HomeScreen.data_layer.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}