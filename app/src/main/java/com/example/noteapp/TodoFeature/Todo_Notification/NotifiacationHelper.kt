package com.example.noteapp.TodoFeature.Todo_Notification

import android.Manifest
import android.R
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission

object NotificationConstants {
    const val CHANNEL_ID = "main_channel"
    const val CHANNEL_NAME = "General Notifications"
}

class NotificationHelper(
    private val context: Context,
    private val manager: NotificationManagerCompat
) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showNotification(title: String, deadline: String, taskId : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        when {
            checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED -> {
                // Already granted
            }
            else -> {
                // Request permission
                val requestPermissionLauncher = context as Activity
                requestPermissionLauncher.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }


        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setSmallIcon(R.drawable.btn_plus)
            .setContentTitle(title)
            .setContentText(deadline)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        manager.notify(taskId, builder.build())
    }

}