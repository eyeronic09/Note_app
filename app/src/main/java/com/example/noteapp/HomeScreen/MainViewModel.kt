package com.example.noteapp.HomeScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel() : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private var noteId = 1


    fun addNote(id: Long, title: String, description: String) {
        val note = Note(id = noteId++, title = title, content = description)
        _notes.value = _notes.value + note
    }

}