package com.example.noteapp.HomeScreen.data_layer.remote.datasource

import android.util.Log
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NotesFirebaseRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : NotesFirebaseRemoteDataSource {

    private fun noteCollection(): CollectionReference {

        val userId = auth.currentUser?.uid
            ?: throw IllegalStateException("User is not logged in")

        return firestore
            .collection("users")
            .document(userId)
            .collection("notes")
    }

    override suspend fun addNote(
        noteEntity: NoteEntity
    ): Result<String> = runCatching {

        val documentRef = noteCollection().document()

        val remoteEntity = noteEntity.copy(
            firebaseNoteId = documentRef.id
        )

        documentRef
            .set(remoteEntity)
            .await()

        Log.d(
            "NotesFirebaseRemoteDS",
            "Note added: ${documentRef.id}"
        )

        documentRef.id
    }

    override suspend fun updateNote(
        noteEntity: NoteEntity
    ): Result<Unit> = runCatching {

        val firebaseNoteId = noteEntity.firebaseNoteId
            ?: throw IllegalStateException(
                "Cannot update note: firebaseNoteId is null"
            )

        noteCollection()
            .document(firebaseNoteId)
            .set(noteEntity)
            .await()

        Log.d(
            "NotesFirebaseRemoteDS",
            "Note updated: $firebaseNoteId"
        )
    }

    override suspend fun deleteNote(
        noteEntity: NoteEntity
    ): Result<Unit> = runCatching {

        val firebaseNoteId = noteEntity.firebaseNoteId
            ?: throw IllegalStateException(
                "Cannot delete note: firebaseNoteId is null"
            )

        noteCollection()
            .document(firebaseNoteId)
            .delete()
            .await()

        Log.d(
            "NotesFirebaseRemoteDS",
            "Note deleted: $firebaseNoteId"
        )
    }

    override suspend fun fetchNotes(): List<NoteEntity> {

        return try {

            val snapshot = noteCollection()
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->

                document
                    .toObject(NoteEntity::class.java)
                    ?.copy(
                        firebaseNoteId = document.id
                    )
            }

        } catch (e: Exception) {

            Log.e(
                "NotesFirebaseRemoteDS",
                "Error fetching notes from Firebase",
                e
            )

            emptyList()
        }
    }
}