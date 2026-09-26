package com.example.noteapp.HomeScreen.data_layer.repository

import android.util.Log
import androidx.work.ListenableWorker
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toDomain
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.data_layer.remote.datasource.NotesFirebaseRemoteDataSource
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RepositoryImpl(
    private val localDatasource: NotesLocalDataSources,
    private val firebaseRemoteDataSource: NotesFirebaseRemoteDataSource,
    private val authRepository: AuthReposistory,
    private val firestore: FirebaseFirestore
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

    override suspend fun getNoteById(noteId: String): Note {
        return localDatasource
            .getNoteById(noteId)
            .toDomain()
    }

    override suspend fun addNote(
        note: Note,
        hasInternet: Boolean
    ) {
        var isSynced = false
        if (hasInternet) {
            try {
                val result = firebaseRemoteDataSource.addNote(note.toEntity())
                if (result.isSuccess) {
                    isSynced = true
                    Log.d("RepositoryImpl", "Note synced to Firebase: ${note.title}")
                } else {
                    Log.e("RepositoryImpl", "Error syncing note to Firebase", result.exceptionOrNull())
                }
            } catch (e: Exception) {
                Log.e("RepositoryImpl", "Error syncing note to Firebase", e)
            }
        }

        try {
            val noteToSave = note.copy(syncedStatus = isSynced)
            localDatasource.addNotes(noteToSave.toEntity()).also {
                Log.d("RepositoryImpl", "Note added to local DB with syncedStatus=$isSynced: ${note.title}")
            }
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error adding note locally", e)
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
        var isSynced = false
        if (hasInternet) {
            try {
                val result = firebaseRemoteDataSource.updateNote(note.toEntity())
                if (result.isSuccess) {
                    isSynced = true
                    Log.d("RepositoryImpl", "Note updated on Firebase")
                } else {
                    Log.e("RepositoryImpl", "Error updating note on Firebase", result.exceptionOrNull())
                }
            } catch (e: Exception) {
                Log.e("RepositoryImpl", "Error updating note on Firebase", e)
            }
        }

        try {
            val noteToSave = note.copy(syncedStatus = isSynced)
            localDatasource.updateNotes(noteToSave.toEntity()).also {
                Log.d("RepositoryImpl", "Note updated in local DB with syncedStatus=$isSynced: ${note.title}")
            }
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error updating note locally", e)
        }
    }

    override suspend fun searchNotes(
        query: String
    ): List<NoteEntity> {
        val userId = authRepository.getCurrentUserId() ?: ""
        return localDatasource.searchNotes(query, userId)
    }

    override suspend fun syncNote()   {
        val userId = authRepository.getCurrentUserId() ?: return
        val allUnsyncedNotes = localDatasource.getAllUnSyncedNotes(currentUser = userId)

        if (allUnsyncedNotes.isEmpty()) {
            return
        }

        for (note in allUnsyncedNotes) {
            firestore.collection("users")
                .document(userId)
                .collection("notes")
                .document(note.id).set(note).await()
            val syncedNote = note.copy(syncedStatus = true)
            localDatasource.updateNotes(syncedNote)
        }

    }
}