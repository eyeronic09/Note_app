package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory

class AddNoteUseCase(private val repository: NoteRepository, private val authRepository: AuthReposistory) {
    suspend operator fun invoke(note: Note) {
        val userId = authRepository.getCurrentUserId() ?: throw Exception("User is not signed in")
        val noteWithUser = note.copy(authUserId = userId)
        repository.addNotes(noteWithUser)
    }
}
