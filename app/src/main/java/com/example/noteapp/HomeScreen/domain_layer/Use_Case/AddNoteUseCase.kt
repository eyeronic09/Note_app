package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import android.util.Log
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory

class AddNoteUseCase(
    private val repository: NoteRepository,
    private val authRepository: AuthReposistory
) {
    suspend operator fun invoke(note: Note, hasInternet : Boolean = false) {
        val userId = authRepository.getCurrentUserId() ?: ""
        val noteWithUser = note.copy(
            firebaseUserId = userId
        )
        Log.d("AddNoteUseCase", "Adding note with userId: '$userId', title: '${note.title}'")
        repository.addNote(
            noteWithUser,
            hasInternet = hasInternet
        )
    }
}
