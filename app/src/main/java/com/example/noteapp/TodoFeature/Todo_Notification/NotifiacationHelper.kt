package com.example.noteapp.TodoFeature.Todo_Notification

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationConstants {
    const val CHANNEL_ID = "main_channel"
    const val CHANNEL_NAME = "General Notifications"
}

class NotificationHelper(
    private val context: Context,
    private val manager: NotificationManagerCompat
) {
    fun showNotification(title: String, deadline: String , taskId : Int) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
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