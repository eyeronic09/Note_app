package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import android.provider.ContactsContract
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository

class PinNoteUseCase(private val noteRepository: NoteRepository)  {
    suspend operator fun invoke(note : Note) {
        val note = note.copy(isPin = !note.isPin)
        noteRepository.updateNotes(note)
    }
}

