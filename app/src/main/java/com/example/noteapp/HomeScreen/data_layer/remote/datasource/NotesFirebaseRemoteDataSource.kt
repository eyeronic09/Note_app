package com.example.noteapp.HomeScreen.data_layer.remote.datasource

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.data_layer.local.mapper.toEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

interface NotesFirebaseRemoteDataSource {

    suspend fun addNote(
        noteEntity: NoteEntity
    ): Result<String>

    suspend fun updateNote(
        noteEntity: NoteEntity
    ): Result<Unit>

    suspend fun deleteNote(
        noteEntity: NoteEntity
    ): Result<Unit>

    suspend fun fetchNotes(): List<NoteEntity>
}