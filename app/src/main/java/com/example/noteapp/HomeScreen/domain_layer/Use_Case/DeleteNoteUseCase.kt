package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend fun invoke(note: Note) {
        return repository.deleteNotes(note)
    }
}