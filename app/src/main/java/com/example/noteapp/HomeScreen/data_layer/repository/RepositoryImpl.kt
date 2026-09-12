package com.example.noteapp.HomeScreen.data_layer.repository

import com.example.noteapp.HomeScreen.data_layer.local.Datasource.FirebaseDataSources
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toDomain
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val localDatasource: NotesLocalDataSources,
    private val remoteDataSources: FirebaseDataSources
) : NoteRepository {
    override fun getNotesNewestFirst(isArchived: Boolean): Flow<List<Note>> {
        return localDatasource.getNotesNewestFirst(isArchived).map { noteEntities -> noteEntities.map {
            it.toDomain()
        } }
    }

    override fun getNotesOldestFirst(isArchived: Boolean): Flow<List<Note>> {
        return localDatasource.getNotesOldestFirst(isArchived).map { noteEntities -> noteEntities.map { it.toDomain() } }
    }

    override suspend fun getNoteById(noteId: Int): Note {
        return localDatasource.getNoteById(noteId).toDomain()
    }

    override suspend fun addNotes(note: Note) {
        localDatasource.addNotes(note.toEntity())
        remoteDataSources.addNotes(note.toEntity())
    }

    override suspend fun deleteNotes(note : Note) {
        localDatasource.deleteNotes(note.toEntity())
        remoteDataSources.deleteNotes(note.toEntity())
    }

    override suspend fun updateNotes(note: Note) {
        localDatasource.updateNotes(note.toEntity())
    }

    override suspend fun searchNotes(query: String): List<NoteEntity> {
       return localDatasource.searchNotes(query)
    }



}
