package com.example.noteapp.HomeScreen.data_layer.local.Datasource

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDataSourcesImpl(private val firestore: FirebaseFirestore) : FirebaseDataSources  {
    override suspend fun addNotes(noteEntity: NoteEntity) {
        firestore.collection("notes").document(noteEntity.id.toString()).set(noteEntity).await()
    }

    override suspend fun deleteNotes(noteEntity: NoteEntity) {
        firestore.collection("notes").document(noteEntity.id.toString()).delete().await()
    }
    }