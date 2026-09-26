package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository

class SyncNotesUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(){
        repository.syncNote()
    }
}