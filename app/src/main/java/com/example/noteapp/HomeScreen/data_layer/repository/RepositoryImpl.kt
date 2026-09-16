package com.example.noteapp.HomeScreen.data_layer.repository

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toDomain
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RepositoryImpl(
    private val localDatasource: NotesLocalDataSources,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : NoteRepository {
    private fun noteCollection() = firestore.collection("users").document(auth.currentUser?.uid ?: "null").collection("notes")
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
        try {
            val userPath = noteCollection().document()
            Log.d("document" , userPath.id + "${userPath.firestore}")
            val entity = note.toEntity().copy(authUserId = userPath.id)
            userPath.set(entity).await().also {
                localDatasource.addNotes(entity)
            }
        } catch (e: Exception) {
            Log.e("RepositoryImpl", "Error adding note", e)
        }
    }

    override suspend fun deleteNotes(note : Note) {
        localDatasource.deleteNotes(note.toEntity())
    }

    override suspend fun updateNotes(note: Note) {
        localDatasource.updateNotes(note.toEntity())
    }

    override suspend fun searchNotes(query: String): List<NoteEntity> {
       return localDatasource.searchNotes(query)
    }

}
