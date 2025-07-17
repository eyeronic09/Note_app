package com.example.noteapp.HomeScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel() : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

     private var noteId = 1

    fun addNote(title: String, description: String) {
        val note = Note(id = noteId++, title = title, content = description)
        noteId++
        _notes.value = _notes.value + note
    }
}