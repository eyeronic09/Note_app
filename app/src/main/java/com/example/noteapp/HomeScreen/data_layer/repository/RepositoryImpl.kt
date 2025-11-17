package com.example.noteapp.HomeScreen.data_layer.repository

import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.entity.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl (private val sources: NotesLocalDataSources) : Repository {
    override fun getAllNotes(): Flow<List<Note>> {
        return sources.getAllNotes()
    }

    override suspend fun addNotes(note: Note) {
        return sources.addNotes(note)
    }

    override suspend fun deleteNotes(note: Note) {
       return sources.deleteNotes(note)
    }

    override suspend fun updateNotes(note: Note) {
        return sources.updateNotes(note)
    }
}