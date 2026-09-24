package com.example.noteapp.HomeScreen.data_layer.repository

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toDomain
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.data_layer.remote.datasource.NotesFirebaseRemoteDataSource
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val localDatasource: NotesLocalDataSources,
    private val firebaseRemoteDataSource: NotesFirebaseRemoteDataSource,
    private val authRepository: AuthReposistory
) : NoteRepository {

    override fun getNotes(hasInternet: Boolean): Flow<List<Note>> = flow {
        val userId = authRepository.getCurrentUserId() ?: ""
        if (hasInternet) {
            try {
                val remoteNotes = firebaseRemoteDataSource.fetchNotes()
                remoteNotes.forEach { noteEntity ->
                    localDatasource.addNotes(noteEntity)
                }
            } catch (e: Exception) {
                Log.e(
                    "RepositoryImpl",
                    "Error fetching notes from remote",
                    e
                )
            }
        }

        emitAll(
            localDatasource
                .getNotesNewestFirst(userId)
                .map { noteEntities ->
                    noteEntities.map { it.toDomain() }
                }
        )
    }

    override suspend fun getNoteById(noteId: Int): Note {
        return localDatasource
            .getNoteById(noteId)
            .toDomain()
    }

    override suspend fun addNote(
        note: Note,
        hasInternet: Boolean
    ) {
        try {
            // Room first
            localDatasource.addNotes(
                note.toEntity()
            )
        } catch (e: Exception) {
            Log.e(
                "RepositoryImpl",
                "Error adding note locally",
                e
            )
        }

        if (hasInternet) {
            try {
                // Firebase second
                firebaseRemoteDataSource.addNote(
                    note.toEntity()
                )
            } catch (e: Exception) {
                Log.e(
                    "RepositoryImpl",
                    "Error syncing note to Firebase",
                    e
                )
            }
        }
    }

    override suspend fun deleteNote(
        note: Note,
        hasInternet: Boolean
    ) {
        try {
            localDatasource.deleteNotes(
                note.toEntity()
            )
        } catch (e: Exception) {
            Log.e(
                "RepositoryImpl",
                "Error deleting note locally",
                e
            )
        }

        if (hasInternet) {
            try {
                firebaseRemoteDataSource.deleteNote(
                    note.toEntity()
                )
            } catch (e: Exception) {
                Log.e(
                    "RepositoryImpl",
                    "Error deleting note from Firebase",
                    e
                )
            }
        }
    }

    override suspend fun updateNote(
        note: Note,
        hasInternet: Boolean
    ) {
        try {
            localDatasource.updateNotes(
                note.toEntity()
            )
        } catch (e: Exception) {
            Log.e(
                "RepositoryImpl",
                "Error updating note locally",
                e
            )
        }

        if (hasInternet) {
            try {
                firebaseRemoteDataSource.updateNote(
                    note.toEntity()
                )
            } catch (e: Exception) {
                Log.e(
                    "RepositoryImpl",
                    "Error updating note on Firebase",
                    e
                )
            }
        }
    }

    override suspend fun searchNotes(
        query: String
    ): List<NoteEntity> {
        val userId = authRepository.getCurrentUserId() ?: ""
        return localDatasource.searchNotes(query, userId)
    }
}