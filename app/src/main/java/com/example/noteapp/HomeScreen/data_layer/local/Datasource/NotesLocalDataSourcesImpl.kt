package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class NotesLocalDataSourcesImpl(private val dao: NotesDao) : NotesLocalDataSources {
    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return dao.getAllNotes()
    }

    override suspend fun addNotes(noteEntity: NoteEntity) {
       return dao.addNotes(noteEntity)
    }

    override suspend fun deleteNotes(noteEntity: NoteEntity) {
        return dao.deleteNotes(noteEntity)
    }

    override suspend fun updateNotes(noteEntity: NoteEntity) {
       return dao.updateNotes(noteEntity)
    }
}