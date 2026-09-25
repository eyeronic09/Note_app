package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class NotesLocalDataSourcesImpl(private val dao: NotesDao) : NotesLocalDataSources {

    override fun getNotesNewestFirst(userId: String): Flow<List<NoteEntity>> {
        return dao.getNotesNewestFirst(userId)
    }

    override suspend fun addNotes(noteEntity: NoteEntity): Result<Unit> {
        return try {
            dao.upsertNote(noteEntity)
            Log.d("NotesLocalDataSource", "DAO addNotes succeeded for entity: id=${noteEntity.id}, title='${noteEntity.title}'")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("NotesLocalDataSource", "DAO addNotes FAILED for entity: ${noteEntity.title}", e)
            Result.failure(e)
        }
    }

    override suspend fun getNoteById(noteId: String): NoteEntity {
        return dao.getNoteById(noteId = noteId)
    }

    override suspend fun searchNotes(query: String, userId: String): List<NoteEntity> {
        return dao.searchNotes(query, userId)
    }

    override suspend fun deleteNotes(noteEntity: NoteEntity) {
        dao.deleteNote(noteEntity)
    }

    override suspend fun updateNotes(noteEntity: NoteEntity) {
        dao.updateNote(noteEntity)
    }

    override suspend fun getAllUnSyncedNotes(currentUser: FirebaseUser?): List<NoteEntity> {
        val userId = currentUser?.uid ?: return emptyList()
        return dao.getAllUnSyncedNotes(userId)
    }
}
