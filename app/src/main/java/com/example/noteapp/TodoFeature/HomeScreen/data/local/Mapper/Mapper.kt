package com.example.noteapp.TodoFeature.HomeScreen.data.local.Mapper

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo


fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date,
        deadlineTimestamp = this.deadlineTimestamp,
        priority = this.priority,
        isCompleted = this.isCompleted
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date,
        deadlineTimestamp = this.deadlineTimestamp,
        priority = this.priority,
        isCompleted = this.isCompleted
    )
}

