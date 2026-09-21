package com.example.noteapp.HomeScreen.data_layer.repository

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toDomain
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.data_layer.remote.datasource.NotesFirebaseRemoteDataSource
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val localDatasource: NotesLocalDataSources,
    private val firebaseRemoteDataSource: NotesFirebaseRemoteDataSource
) : NoteRepository {

    override fun getNotesNewestFirst(isArchived: Boolean): Flow<List<Note>> {
        return localDatasource.getNotesNewestFirst(isArchived).map { noteEntities ->
            Log.d("RepositoryImpl", "Received ${noteEntities.size} NoteEntities from Room DAO")
            noteEntities.map { it.toDomain() }
        }
    }

    override suspend fun getNoteById(noteId: Int): Note {
        return localDatasource.getNoteById(noteId).toDomain()
    }

    override suspend fun addNotes(note: Note): Unit = withContext(Dispatchers.IO + NonCancellable) {
        try {
            val result = localDatasource.addNotes(note.toEntity())
            if (result.isFailure) {
                Log.e("RepositoryImpl", "Error saving note locally: ${result.exceptionOrNull()}")
            } else {
                Log.d("RepositoryImpl", "Successfully saved note locally: title='${note.title}'")
            }
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error adding note locally", e)
        }

        try {
            firebaseRemoteDataSource.addNote(note.toEntity())
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error syncing note to Firebase", e)
        }
    }

    override suspend fun deleteNotes(note: Note): Unit = withContext(Dispatchers.IO + NonCancellable) {
        try {
            localDatasource.deleteNotes(note.toEntity())
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error deleting note locally", e)
        }

        try {
            firebaseRemoteDataSource.deleteNote(note.toEntity())
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error deleting note from Firebase", e)
        }
    }

    override suspend fun updateNotes(note: Note): Unit = withContext(Dispatchers.IO + NonCancellable) {
        try {
            localDatasource.updateNotes(note.toEntity())
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error updating note locally", e)
        }

        try {
            firebaseRemoteDataSource.updateNote(note.toEntity())
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error updating note on Firebase", e)
        }
    }

    override suspend fun searchNotes(query: String): List<NoteEntity> {
        return localDatasource.searchNotes(query)
    }
}
