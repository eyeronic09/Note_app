package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNoteByIdUseCase (private val repository: NoteRepository){
    suspend operator fun invoke(id: Int): Note {
        return repository.getNoteById(id)
    }
}