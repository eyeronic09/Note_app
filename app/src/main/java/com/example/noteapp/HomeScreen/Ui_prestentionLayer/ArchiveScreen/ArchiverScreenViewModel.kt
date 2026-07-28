package com.example.noteapp.HomeScreen.Ui_prestentionLayer.ArchiveScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.GetAllNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.NoteUseCases
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface UiState {
    data object Loading : UiState

    data class Error(val meassage : String) : UiState
    data class Content(
        val listOfNote: List<Note> = emptyList(),

    )
}

sealed interface ArchiverEvent {
    data class unArchiver(val note: Note) : ArchiverEvent
}



class ArchiverScreenViewModel(
    private val reposistory: NoteUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()

    fun onEvent(event: ArchiverEvent) {
        when(event) {
            is ArchiverEvent.unArchiver -> {
                unArchiver(note = event.note)
            }
        }
    }


    fun unArchiver(note: Note) {
        viewModelScope.launch {
            reposistory.unArchiverUseCases.invoke(note)
        }

    }
}