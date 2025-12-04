package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class NotesLocalDataSourcesImpl(private val dao: NotesDao) : NotesLocalDataSources {
    override fun getNotesNewestFirst(): Flow<List<NoteEntity>> {
        return dao.getNotesNewestFirst()
    }

    override fun getNotesOldestFirst(): Flow<List<NoteEntity>> {
        return dao.getNotesOldestFirst()
    }


    override suspend fun addNotes(noteEntity: NoteEntity) {
       return dao.addNotes(noteEntity)
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