package com.example.noteapp.HomeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.noteapp.HomeScreen.data_layer.local.entity.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class MainViewModel() : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var noteId = 1

    @RequiresApi(Build.VERSION_CODES.O)
    private val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote(title: String, description: String, date: String, colors: Color = getPastelColor()) {
        val note = Note(
            id = noteId++,
            title = title,
            content = description,
            date = currentDate,
            color = colors
        )
        _notes.value = _notes.value + note
    }

    fun deleteNote(note: Note) {
        _notes.value = _notes.value - note
    }
    
    fun updateNote(updatedNote: Note) {
        _notes.value = _notes.value.map { note ->
            if (note.id == updatedNote.id) updatedNote else note
        }
    }

    fun getPastelColor(): Color {
        val predefinedColors = listOf(
            Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta
        )
        return predefinedColors.random()


    }

}