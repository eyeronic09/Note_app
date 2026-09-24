package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory

class DeleteNoteUseCase(
    private val repository: NoteRepository ,
    private val authRepository: AuthReposistory
) {
    suspend operator fun invoke(note: Note , hasInternet: Boolean = false) {
        val userId = authRepository.getCurrentUserId() ?: ""
        val noteWithUser  = note.copy(firebaseNoteId = userId , syncedStatus = hasInternet)
        repository.deleteNote(noteWithUser, hasInternet)
    }
}