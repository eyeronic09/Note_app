package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.graphics.Color.argb
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



data class HomeScreenUIState(
    val title: String = "",
    val content: String = "",
    val notes: List<Note> = emptyList(),
    val error: String? = null,
    val currentNoteId: Int? = null,
    val isLoading: Boolean = false,
    val isWriting : Boolean = false,
    val color : Int? = null,
    var searchedText : String  = "",
    val isSearching : Boolean = false,
    val searchResult: List<Note> = emptyList()

)

sealed interface  HomeScreenEvent {
    data object SetToEdit : HomeScreenEvent

    data class UpdateTitle(val title: String) : HomeScreenEvent
    data class UpdateContent(val content: String) : HomeScreenEvent
    data class AddNote(val content: String) : HomeScreenEvent
    data class DeleteNote(val note: Note) : HomeScreenEvent
    data object UpdateNote : HomeScreenEvent
    data class OpenToReadAndUpdate(val noteId: Int) : HomeScreenEvent
    object LoadNotes : HomeScreenEvent
    data class OnSearchQueryChanged(val query: String) : HomeScreenEvent
    data object ShowResult : HomeScreenEvent
}

@OptIn(FlowPreview::class)
class HomeScreenViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                }

            }
            is HomeScreenEvent.SetToEdit -> {
                setToEditMode()
            }
            is HomeScreenEvent.AddNote -> {
                insertNote()
            }
            is HomeScreenEvent.OpenToReadAndUpdate -> {
                loadNoteById(event.noteId)
            }
            is HomeScreenEvent.UpdateContent -> {
                _uiState.update { it.copy(content = event.content) }
            }

            is HomeScreenEvent.UpdateTitle -> {
                _uiState.update {
                    it.copy(title = event.title)
                }
            }
            is HomeScreenEvent.UpdateNote -> {
                updateNote()
            }
            is HomeScreenEvent.OnSearchQueryChanged -> {
                onSearchQueryChange(event.query)
            }
            is HomeScreenEvent.LoadNotes -> {
                print("loaded")
            }

            is HomeScreenEvent.ShowResult -> search()
        }
    }

    init {
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

    fun search() {
        if(_uiState.value.isSearching) {
            viewModelScope.launch {
                val allNotesFlow = repository.getAllNotes()
                val searchedTextFlow = _uiState.map { it.searchedText }.distinctUntilChanged()
                allNotesFlow
                    // The `debounce(300L)` operator on the text flow is crucial: it waits for 300 milliseconds of inactivity before emitting the latest text.
                    // This prevents the app from searching on every single keystroke, improving performance.
                    .combine(searchedTextFlow.debounce(300L)) { notes, text ->
                        Log.d("Query", uiState.value.searchedText.toString())
                        if (text.isBlank()) {
                            notes
                        } else {
                            notes.filter { note ->
                                note.title.contains(text, ignoreCase = true) ||
                                        note.content.contains(text, ignoreCase = true)
                            }
                        }
                    }
                    .collectLatest { filteredNotes ->
                        _uiState.update {
                            it.copy(
                                notes = filteredNotes,
                                isLoading = false
                            )
                        }
                    }
            }
        }
    }


    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchedText =  query,
                isSearching = true
            )
        }
        search()

    }

    private fun setToEditMode(){
        _uiState.update {
            it.copy(
                isWriting = !_uiState.value.isWriting
            ).also {
                Log.d("Current Mode" , _uiState.value.isWriting.toString())
            }
        }
    }
    private fun randomColor(): Int {
        val colors = listOf(
            argb(255,246, 114, 128),
            argb(255,192, 108, 132),
            argb(255, 108, 91, 123),
            argb(255, 174, 222, 252)
        )

        return colors.random()
    }

    private suspend fun deleteNote(note: Note){
        repository.deleteNotes(note)
    }

    private fun updateNote(){
        if (_uiState.value.isWriting){
            val noteId = _uiState.value.currentNoteId ?: return

            viewModelScope.launch {
                try {
                    _uiState.update { it.copy(isLoading = true) }
                    val updatedNote = Note(
                        id = noteId,
                        title = _uiState.value.title,
                        content = _uiState.value.content,
                        date = "",
                        color = _uiState.value.color ?: randomColor()
                    )

                    repository.updateNotes(updatedNote)

                    _uiState.update {
                        it.copy(
                            title = "",
                            content = "",
                            currentNoteId = null,
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(
                        error = "Failed to update note",
                        isLoading = false
                    )}
                }
            }
        }
    }

    private fun loadNoteById(noteId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val note = repository.getNoteById(noteId)
                _uiState.update { currentState ->
                    currentState.copy(
                        title = note.title,
                        content = note.content,
                        currentNoteId = noteId,
                        isLoading = false,
                        color = note.color
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(
                    error = "Failed to load note",
                    isLoading = false
                )}
            }
        }
    }

    private fun insertNote() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val note = Note(
                    title = _uiState.value.title,
                    content = _uiState.value.content, // Use content from parameter
                    date = "",
                    color = randomColor()
                )
                repository.addNotes(note).also {
                    Log.d("Add_Note", note.toString())
                }
                // Reset state after adding
                 _uiState.update { it.copy(
                    title = "",
                    content = "",
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    error = "Failed to save note",
                    isLoading = false
                )}
            }
        }
    }
}