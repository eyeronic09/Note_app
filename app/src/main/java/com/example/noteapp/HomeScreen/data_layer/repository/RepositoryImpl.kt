package com.example.noteapp.HomeScreen.data_layer.repository

import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toDomain
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val sources: NotesLocalDataSources
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return sources.getAllNotes().map { noteEntities -> noteEntities.map {
            it.toDomain()
        } }
    }

    override suspend fun addNotes(note: Note) {
        sources.addNotes(note.toEntity())
    }

    override suspend fun deleteNotes(note : Note) {
        sources.deleteNotes(note.toEntity())
    }

    override suspend fun updateNotes(note: Note) {
        sources.updateNotes(note.toEntity())
    }

}
