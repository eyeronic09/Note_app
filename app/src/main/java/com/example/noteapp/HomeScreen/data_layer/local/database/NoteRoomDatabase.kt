package com.example.noteapp.HomeScreen.data_layer.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(NoteConverters::class)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}