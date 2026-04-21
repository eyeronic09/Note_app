package com.example.noteapp.TodoFeature.HomeScreen.data.local.Mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import java.time.LocalDateTime
import java.time.ZoneId


fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        time = this.time,
        priority = this.priority,
        isCompleted = this.isCompleted
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        time = this.time,
        priority = this.priority,
        isCompleted = this.isCompleted
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Todo.toTimestamp(): Long {
    // Combine date and time (fallback to midnight if time is null)
    val localDateTime = LocalDateTime.of(this.date, this.time ?: java.time.LocalTime.MIDNIGHT)

    // Convert to milliseconds based on system timezone
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}