package com.example.noteapp.HomeScreen.Ui_prestentionLayer.ArchiveScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.GetAllNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.NoteUseCases
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface ArchiverStateUiState {
    data object Loading : ArchiverStateUiState

    data class Error(val meassage : String) : ArchiverStateUiState
    data class Content(
        val listOfNote: List<Note> = emptyList(),

    ): ArchiverStateUiState
}

sealed interface ArchiverEvent {
    data class unArchiver(val note: Note) : ArchiverEvent
}



class ArchiverScreenViewModel(
    private val reposistory: NoteUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArchiverStateUiState.Content())
    val uiState : StateFlow<ArchiverStateUiState.Content> = _uiState.asStateFlow()

    fun onEvent(event: ArchiverEvent) {
        when(event) {
            is ArchiverEvent.unArchiver -> {
                unArchiver(note = event.note)
            }
        }
    }

    init {
        viewModelScope.launch {
            reposistory.getAllArchiverUseCase.invoke().collect { note ->
                Log.d("ArchiverScreenViewModel", "Emitted notes: $note")
                _uiState.update { it ->
                    it.copy(listOfNote = note)
                }
            }
        }
    }


    fun unArchiver(note: Note) {
        viewModelScope.launch {
            reposistory.unArchiverUseCases.invoke(note)
        }

    }
}