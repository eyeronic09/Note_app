package com.example.noteapp.TodoFeature.HomeScreen.data.local.Mapper

import com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity.TodoEntity
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo

object Mapper {
    fun TodoToDomain(entity: TodoEntity) : Todo {
        return Todo(
            id = entity.id,
            title = entity.title,
            content = entity.content.toString(),
            date = entity.date,
            deadlineTimestamp = entity.deadlineTimestamp.toString(),
            priority = entity.priority,
            isCompleted = entity.isCompleted
        )
    }
}