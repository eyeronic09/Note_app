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
    suspend fun addNote(noteEntity: NoteEntity): Result<String> // Returns generated Firestore Document ID
    suspend fun updateNote(noteEntity: NoteEntity): Result<Unit>
    suspend fun deleteNote(noteEntity: NoteEntity): Result<Unit>
    fun fetchNotes(): Flow<List<NoteEntity>>
}

class NotesFirebaseRemoteDataSourceImpl(
    private val auth : FirebaseAuth,
    private val firestore : FirebaseFirestore
): NotesFirebaseRemoteDataSource{

    private fun noteCollection() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?:"null")
            .collection("notes").also {
                Log.d("collections" , "firestorm ${it.path} and ${it.parent}")
            }


    override suspend fun addNote(noteEntity: NoteEntity) : Result<String> = runCatching{
        val documentRef = noteCollection().document() // this part is path with users and there notes entity
        val remoteEntity = noteEntity.copy(authUserId = documentRef.id)
        documentRef.set(remoteEntity).await()
        documentRef.id.also {
            it -> Log.d("collections" , it)
        }
    }


    override suspend fun updateNote(noteEntity: NoteEntity): Result<Unit> = runCatching{
        if (noteEntity.authUserId.isNotBlank()){
            noteCollection().document(noteEntity.authUserId).set(noteEntity).await()
        }
    }

    override suspend fun deleteNote(noteEntity: NoteEntity): Result<Unit> = runCatching{
        firestore.collection("users").document().collection("notes").document("noteId").set(noteEntity).await()

    }

    override fun fetchNotes(): Flow<List<NoteEntity>> {
        TODO("Not yet implemented")
    }

}