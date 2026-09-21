package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class NotesLocalDataSourcesImpl(private val dao: NotesDao) : NotesLocalDataSources {

    override fun getNotesNewestFirst(isArchived: Boolean): Flow<List<NoteEntity>> {
        return dao.getNotesNewestFirst(isArchived)
    }

    override suspend fun addNotes(noteEntity: NoteEntity): Result<Unit> {
        return try {
            dao.addNotes(noteEntity)
            Log.d("NotesLocalDataSource", "DAO addNotes succeeded for entity: id=${noteEntity.id}, title='${noteEntity.title}'")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("NotesLocalDataSource", "DAO addNotes FAILED for entity: ${noteEntity.title}", e)
            Result.failure(e)
        }
    }

    override suspend fun getNoteById(noteId: Int): NoteEntity {
        return dao.getNoteById(noteId = noteId)
    }

    override suspend fun searchNotes(query: String): List<NoteEntity> {
        return dao.searchNotes(query)
    }

    override suspend fun deleteNotes(noteEntity: NoteEntity) {
        return dao.deleteNotes(noteEntity)
    }

    override suspend fun updateNotes(noteEntity: NoteEntity) {
        return dao.updateNotes(noteEntity)
    }
}
