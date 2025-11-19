package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeScreenUIState(
    val notes: List<Note> = emptyList(),
    val isLoading : Boolean = false,
    val error: String? = null
)

sealed interface  HomeScreenEvent {
    data class AddNote(val title: String, val content: String) : HomeScreenEvent
    data class UpdateNote(val note: Note) : HomeScreenEvent
    data class DeleteNote(val note: Note) : HomeScreenEvent
    data class OpenToRead(val note: Note) : HomeScreenEvent
    object LoadNotes : HomeScreenEvent
}

class HomeScreenViewModel(private val repository: NoteRepository) : ViewModel(){

    private val _uiState = MutableStateFlow <HomeScreenUIState>(HomeScreenUIState())
    val uiState : StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    fun onEvent(onClick : HomeScreenEvent) {
        when(onClick){
            HomeScreenEvent.LoadNotes -> loadNotes()
            is HomeScreenEvent.AddNote -> TODO()
            is HomeScreenEvent.DeleteNote -> TODO()
            is HomeScreenEvent.OpenToRead -> TODO()
            is HomeScreenEvent.UpdateNote -> TODO()
        }
    }
    init {
        loadNotes()
    }
    private fun loadNotes(){
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
            }catch (e: Exception){
                Log.d("HomeScreen_error" , e.toString())
            }
        }
    }
}