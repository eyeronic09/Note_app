package com.example.noteapp.HomeScreen.domain_layer.repository

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesNewestFirst(): Flow<List<Note>>

    fun getNotesOldestFirst() : Flow<List<Note>>

    suspend fun getNoteById(noteId: Int): Note
    suspend fun addNotes(note: Note)
    suspend fun deleteNotes(note: Note)
    suspend fun updateNotes(note: Note)

    suspend fun searchNotes(query: String) : List<NoteEntity>
}