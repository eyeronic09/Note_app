package com.example.noteapp.TodoFeature.Todo_Notification.Scheduler

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.noteapp.R


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, p1: Intent?) {
        val message = p1?.getStringExtra("EXTRA_TITLE") ?: "You have some task to do"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        val notification = NotificationCompat.Builder(context, "todo_channel") // Use your actual CHANNEL_ID
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your icon
            .setContentTitle(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager?.notify(1, notification)


    }
}