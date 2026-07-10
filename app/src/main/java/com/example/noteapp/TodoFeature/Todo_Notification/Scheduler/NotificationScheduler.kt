package com.example.noteapp.TodoFeature.Todo_Notification.Scheduler


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.noteapp.TodoFeature.Todo_Notification.Model.NotificationContent
import com.example.noteapp.TodoFeature.Todo_Notification.NotificationDataSource.NotificationActions
import java.time.ZoneId


class NotificationScheduler(
    private val context : Context
): NotificationActions{
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun scheduler(item: NotificationContent){
        val intent = Intent( context , NotificationReceiver::class.java).apply {
            putExtra("EXTRA_TITLE" , item.todoMessage)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.triggerTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


    override fun cancel(item: NotificationContent) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

}