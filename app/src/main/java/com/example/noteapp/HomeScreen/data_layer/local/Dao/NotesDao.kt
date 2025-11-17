package com.example.noteapp.HomeScreen.data_layer.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.noteapp.HomeScreen.data_layer.local.entity.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ")
    fun getAllNotes(): Flow<List<Note>>

    @Upsert
   suspend fun addNotes(note: Note)

    @Delete
    suspend fun deleteNotes(note: Note)

    @Update
    suspend fun updateNotes(note: Note)
}