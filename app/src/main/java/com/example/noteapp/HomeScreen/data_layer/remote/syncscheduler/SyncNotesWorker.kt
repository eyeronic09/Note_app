package com.example.noteapp.HomeScreen.data_layer.remote.syncscheduler

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.SyncNotesUseCase

class SyncNotesWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val noteUseCase: SyncNotesUseCase
) : CoroutineWorker(appContext = context, params = workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            noteUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
