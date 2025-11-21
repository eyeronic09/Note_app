package com.example.noteapp.HomeScreen.data_layer.local.mapper

import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note

fun NoteEntity.toDomain() : Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date
    )
}



fun Note.toEntity() : NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date
    )
}