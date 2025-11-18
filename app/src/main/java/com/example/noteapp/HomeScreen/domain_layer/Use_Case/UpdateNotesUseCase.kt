package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository

class UpdateNotesUseCase(private val repository: NoteRepository){
    suspend operator fun invoke(note: Note) {
        return repository.updateNotes(note)
    }
}