package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository

class UnArchiverUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note, interent : Boolean = false){
        return repository.updateNote(
            note.copy(isArchived = !note.isArchived),
            hasInternet = interent
        )
    }
}
