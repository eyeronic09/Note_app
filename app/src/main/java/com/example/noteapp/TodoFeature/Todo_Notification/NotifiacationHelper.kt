package com.example.noteapp.TodoFeature.Todo_Notification

import android.Manifest
import android.R
import android.app.Notification
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(
    private val context: Context
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification() {
        val builder = NotificationCompat.Builder(context, "my_channel_id")
            .setSmallIcon(R.drawable.btn_plus)
            .setContentTitle("hello ")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }
}