package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesLocalDataSources {
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun addNotes(noteEntity: NoteEntity)
     suspend fun getNoteById(noteId: Int): NoteEntity
    suspend fun deleteNotes(noteEntity: NoteEntity)
    suspend fun updateNotes(noteEntity: NoteEntity)
}