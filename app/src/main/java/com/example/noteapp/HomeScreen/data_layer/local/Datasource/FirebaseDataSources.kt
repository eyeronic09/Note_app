package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity

interface FirebaseDataSources {
    suspend fun addNotes(noteEntity: NoteEntity)
    suspend fun deleteNotes(noteEntity: NoteEntity)
    

}