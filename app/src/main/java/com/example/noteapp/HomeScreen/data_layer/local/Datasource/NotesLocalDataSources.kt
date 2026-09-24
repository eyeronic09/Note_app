package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesLocalDataSources {
    fun getNotesNewestFirst(userId: String): Flow<List<NoteEntity>>

    suspend fun addNotes(noteEntity: NoteEntity): Result<Unit>
    suspend fun getNoteById(noteId: Int): NoteEntity

    suspend fun searchNotes(query: String, userId: String): List<NoteEntity>
    suspend fun deleteNotes(noteEntity: NoteEntity)
    suspend fun updateNotes(noteEntity: NoteEntity)
}
