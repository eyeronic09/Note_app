package com.example.noteapp.HomeScreen.data_layer.local.mapper

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note

fun NoteEntity.toDomain() : Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        firebaseUserId = this.firebaseUserId,
        date = this.date,
        updatedAt = this.updatedAt,
        color = this.color,
        listOfImageUri = this.listOfImageUri,
        isPin = this.isPin,
        isArchived = this.isArchived,
        category = this.category,
        syncedStatus = this.syncedStatus
    )
}

fun Note.toEntity() : NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date,
        updatedAt = this.updatedAt,
        firebaseUserId = this.firebaseUserId,
        color = this.color,
        listOfImageUri = this.listOfImageUri,
        category = this.category,
        isPin = this.isPin,
        isArchived = this.isArchived,
        syncedStatus = this.syncedStatus
    )
}