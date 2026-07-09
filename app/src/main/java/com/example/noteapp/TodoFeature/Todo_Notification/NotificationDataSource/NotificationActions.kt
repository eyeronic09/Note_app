package com.example.noteapp.TodoFeature.Todo_Notification.NotificationDataSource

import com.example.noteapp.TodoFeature.Todo_Notification.Model.NotificationContent

interface NotificationActions {
    fun scheduler(item : NotificationContent)
    fun cancel(item: NotificationContent)

}
