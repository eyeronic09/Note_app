package com.example.noteapp.HomeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel() : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()



     private var noteId = 1

    @RequiresApi(Build.VERSION_CODES.O)
    private val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote(title: String, description: String , date: String) {
        val note = Note(id = noteId++, title = title, content = description , date = currentDate)
        noteId++
        _notes.value = _notes.value + note
    }
    fun deleteNote(note: Note){
        _notes.value = _notes.value - note
    }
    
}