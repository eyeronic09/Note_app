package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.entity.Note
import kotlinx.coroutines.flow.Flow

class NotesLocalDataSourcesImpl(private val dao: NotesDao) : NotesLocalDataSources {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }

    override suspend fun addNotes(note: Note) {
       return dao.addNotes(note)
    }

    override suspend fun deleteNotes(note: Note) {
        return dao.deleteNotes(note)
    }

    override suspend fun updateNotes(note: Note) {
       return dao.updateNotes(note)
    }
}