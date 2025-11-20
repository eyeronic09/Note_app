package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeScreenUIState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val title: String = "" ,
    val content: String = ""
)

sealed interface  HomeScreenEvent {
    data class UpdateTitle(val title: String) : HomeScreenEvent
    data class UpdateContent(val content: String) : HomeScreenEvent

    data object AddNote : HomeScreenEvent
    data class UpdateNote(val note: Note) : HomeScreenEvent
    data class DeleteNote(val note: Note) : HomeScreenEvent
    data class OpenToRead(val note: Note) : HomeScreenEvent
    object LoadNotes : HomeScreenEvent
}

class HomeScreenViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.LoadNotes -> loadNotes()
            is HomeScreenEvent.DeleteNote -> TODO()
            is HomeScreenEvent.AddNote -> insertNote()
            is HomeScreenEvent.OpenToRead -> TODO()
            is HomeScreenEvent.UpdateNote -> TODO()
            is HomeScreenEvent.UpdateContent -> {
                _uiState.update { it.copy(content = event.content) }
            }

            is HomeScreenEvent.UpdateTitle -> {
                _uiState.update {
                    it.copy(title = event.title)
                }
            }

            else -> {}
        }
    }

    fun insertNote() {
        viewModelScope.launch {
            val note = Note(
                title = _uiState.value.title,
                content = _uiState.value.content,
                date = ""
            )
            repository.addNotes(note).also {
                Log.d("Add_Note", note.toString())
            }
        }


    }

    fun loadNotes() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.getAllNotes().collect { notes ->
                    _uiState.update { state ->
                        state.copy(
                            notes = notes,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("HomeScreen_error", e.toString())
            }
        }
    }
}