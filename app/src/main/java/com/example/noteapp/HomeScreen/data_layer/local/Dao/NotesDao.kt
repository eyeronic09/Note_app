package com.example.noteapp.HomeScreen.data_layer.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Upsert
   suspend fun addNotes(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNotes(noteEntity: NoteEntity)

    @Update
    suspend fun updateNotes(noteEntity: NoteEntity)
}