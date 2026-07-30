package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map

class GetAllArchiverUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke() : Flow<List<Note>> {
        return repository.getNotesNewestFirst().map { notes -> notes.filter { it.isArchived }}
    }
}