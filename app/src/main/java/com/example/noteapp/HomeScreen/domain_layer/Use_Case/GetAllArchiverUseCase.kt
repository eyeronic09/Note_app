package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllArchiverUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke() : Flow<List<Note>> {
        return repository.getNotesNewestFirst(isArchived = true)
    }
}