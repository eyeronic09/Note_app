package com.example.noteapp.TodoFeature.Todo_Notification.Model

import java.time.LocalDate
import java.time.LocalDateTime

data class NotificationContent(
    val triggerTime : LocalDateTime,
    val todoMessage : String
)
