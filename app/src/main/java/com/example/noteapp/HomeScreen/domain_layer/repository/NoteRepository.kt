package com.example.noteapp.HomeScreen.domain_layer.repository

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import kotlinx.coroutines.flow.Flow
interface NoteRepository {

    fun getNotes(
        hasInternet: Boolean = false
    ): Flow<List<Note>>

    suspend fun getNoteById(
        noteId: String
    ): Note

    suspend fun addNote(
        note: Note,
        hasInternet: Boolean
    )

    suspend fun deleteNote(
        note: Note,
        hasInternet: Boolean
    )

    suspend fun updateNote(
        note: Note,
        hasInternet: Boolean
    )

    suspend fun searchNotes(
        query: String
    ): List<NoteEntity>
    suspend fun syncNote() 
}