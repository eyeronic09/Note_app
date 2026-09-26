package com.example.noteapp.HomeScreen.data_layer.remote.syncscheduler

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class NoteSyncScheduler(
    private val syncWorkManager: WorkManager
){
    val internetConstraints = Constraints.Builder()
        .setRequiredNetworkType(
            NetworkType.CONNECTED,
        ).build()


    fun scheduleSync(){
        val request = OneTimeWorkRequestBuilder<SyncNotesWorker>()
            .setConstraints(internetConstraints).build()
        syncWorkManager.enqueue(request)
    }

}