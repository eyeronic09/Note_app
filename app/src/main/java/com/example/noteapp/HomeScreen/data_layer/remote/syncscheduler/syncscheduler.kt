package com.example.noteapp.HomeScreen.data_layer.remote.syncscheduler

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class syncsecheduler(
    context: Context,
    workerParameters: WorkerParameters,
    private val local: NotesLocalDataSources,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : CoroutineWorker(appContext = context, params = workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure()
            val allUnsyncedNotes = local.getAllUnSyncedNotes(currentUser)

            if (allUnsyncedNotes.isEmpty()) {
                return Result.success()
            } else if (currentUser.uid.isEmpty()) {
                return Result.failure()
            }

            fun noteRef() = firestore
                .collection("users")
                .document(currentUser.uid)
                .collection("notes")

            for (note in allUnsyncedNotes) {
                noteRef().document(note.id).set(note).await()
                val syncedNote = note.copy(syncedStatus = true)
                local.updateNotes(syncedNote)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
