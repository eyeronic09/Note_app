package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home

import android.annotation.SuppressLint
import android.graphics.Color.argb
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.NoteOrder
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.NoteUseCases
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.OrderType
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HomeScreenUIState(
    val notes: List<Note> = emptyList(),
    val noteEditor: NoteEditor = NoteEditor(),
    val currentNoteId: Int? = null,
    val color: Int? = null,
    val searchedText: String = "",
    val isSearching: Boolean = false,
    val noteOrder: NoteOrder = NoteOrder.Title(
        orderType = OrderType.Ascending,
    ),
    val isPin: Boolean = false,
    val isOrderSectionVisibility: Boolean = false
)

data class NoteEditor(
    val title: String = "",
    val content: String = "",
    val imageUri: List<Uri> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val isWriting: Boolean = false,
)

sealed interface HomeScreenEvent {
    data class ToggleArchiver(val note: Note) : HomeScreenEvent
    data object SetToEdit : HomeScreenEvent
    data class PinNote(val note: Note) : HomeScreenEvent
    object ToggleOrderSection : HomeScreenEvent
    data class Order(val noteOrder: NoteOrder) : HomeScreenEvent

    data class UpdateTitle(val title: String) : HomeScreenEvent
    data class UpdateContent(val content: String) : HomeScreenEvent
    object AddNote : HomeScreenEvent
    data class DeleteNote(val note: Note) : HomeScreenEvent
    data object UpdateNote : HomeScreenEvent
    data class OpenToReadAndUpdate(val noteId: Int) : HomeScreenEvent
    object LoadNotes : HomeScreenEvent
    data class OnSearchQueryChanged(val query: String) : HomeScreenEvent
    data object ShowResult : HomeScreenEvent
    data object TapToSearch : HomeScreenEvent
    data object CloseSearch : HomeScreenEvent

    data class OnImageSelected(val uris: List<Uri>) : HomeScreenEvent
}

@OptIn(FlowPreview::class)
class HomeScreenViewModel(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                _uiState.update { it.copy(noteEditor = it.noteEditor.copy(content = event.content)) }
            }

            is HomeScreenEvent.UpdateTitle -> {
                _uiState.update { it.copy(noteEditor = it.noteEditor.copy(title = event.title)) }
            }

            is HomeScreenEvent.UpdateNote -> {
                updateNote()
            }

            is HomeScreenEvent.OnSearchQueryChanged -> {
                onSearchQueryChange(event.query)
            }

            is HomeScreenEvent.LoadNotes -> {
                getNotes(_uiState.value.noteOrder)
            }

            is HomeScreenEvent.ShowResult -> {
                search()
            }

            is HomeScreenEvent.TapToSearch -> {
                _uiState.update { it.copy(isSearching = true) }
            }

            is HomeScreenEvent.CloseSearch -> {
                _uiState.update { it.copy(isSearching = false, searchedText = "") }
                getNotes(_uiState.value.noteOrder)
            }

            is HomeScreenEvent.OnImageSelected -> {
                _uiState.update {
                    it.copy(
                        noteEditor = it.noteEditor.copy(imageUri = it.noteEditor.imageUri + event.uris)
                    )
                }
            }

            HomeScreenEvent.ToggleOrderSection -> {
                _uiState.update {
                    it.copy(isOrderSectionVisibility = !it.isOrderSectionVisibility)
                }
            }

            is HomeScreenEvent.Order -> {
                if (_uiState.value.noteOrder == event.noteOrder) {
                    return
                }
                getNotes(event.noteOrder)
            }

            is HomeScreenEvent.PinNote -> {
                pinNote(event.note)
            }

            is HomeScreenEvent.ToggleArchiver -> {
                toggleArchiver(event.note)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getAllNoteUseCase(noteOrder).onEach { notesList ->
            Log.d("HomeScreenViewModel", "Fetched notes count: ${notesList.size}")
            _uiState.update { currentState ->
                currentState.copy(
                    notes = notesList,
                    noteOrder = noteOrder
                )
            }
        }.launchIn(viewModelScope)
    }

    fun toggleArchiver(note: Note) {
        viewModelScope.launch {
            noteUseCases.unArchiverUseCases.invoke(note)
        }
    }

    fun search() {
        if (_uiState.value.isSearching) {
            viewModelScope.launch {
                val allNotesFlow = noteUseCases.getAllNoteUseCase()
                val searchedTextFlow = _uiState.map { it.searchedText }.distinctUntilChanged()
                allNotesFlow
                    // This prevents the app from searching on every single keystroke, improving performance.
                    .combine(searchedTextFlow.debounce(300L)) { notesList, text ->
                        if (text.isBlank()) {
                            notesList
                        } else {
                            notesList.filter { note ->
                                note.title.contains(text, ignoreCase = true) ||
                                        note.content.contains(text, ignoreCase = true)
                            }
                        }
                    }
                    .collect { filteredNotes ->
                        _uiState.update {
                            it.copy(
                                notes = filteredNotes,
                                noteEditor = it.noteEditor.copy(isLoading = false)
                            )
                        }
                    }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchedText = query,
                isSearching = true
            )
        }
        search()
    }

    private fun setToEditMode() {
        _uiState.update { currentState ->
            val newWritingState = !currentState.noteEditor.isWriting
            Log.d("Current Mode", newWritingState.toString())
            currentState.copy(
                noteEditor = currentState.noteEditor.copy(isWriting = newWritingState)
            )
        }
    }

    private fun randomColor(): Int {
        val colors = listOf(
            argb(255, 246, 114, 128),
            argb(255, 192, 108, 132),
            argb(255, 108, 91, 123),
            argb(255, 174, 222, 252)
        )
        return colors.random()
    }

    private suspend fun deleteNote(note: Note) {
        noteUseCases.deleteNoteUseCase(note)
    }

    private fun updateNote() {
        val editor = _uiState.value.noteEditor
        val noteId = _uiState.value.currentNoteId ?: return

        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(noteEditor = it.noteEditor.copy(isLoading = true))
                }
                val existingNote = _uiState.value.notes.find { it.id == noteId }
                val currentDate = SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(Date())

                val updatedNote = Note(
                    id = noteId,
                    title = editor.title,
                    content = editor.content,
                    date = existingNote?.date?.ifBlank { currentDate } ?: currentDate,
                    color = _uiState.value.color ?: randomColor(),
                    listOfImageUri = editor.imageUri.map { it.toString() }
                )

                noteUseCases.updateNotesUseCase(updatedNote)

                _uiState.update {
                    it.copy(
                        noteEditor = it.noteEditor.copy(
                            isLoading = false,
                            isWriting = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        noteEditor = it.noteEditor.copy(
                            error = "Failed to update note",
                            isLoading = false
                        )
                    )
                }
            }
        }
    }

    private fun loadNoteById(noteId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(noteEditor = it.noteEditor.copy(isLoading = true))
                }
                val note = noteUseCases.getNoteByIdUseCase(noteId)
                _uiState.update { currentState ->
                    currentState.copy(
                        currentNoteId = noteId,
                        color = note.color,
                        noteEditor = currentState.noteEditor.copy(
                            title = note.title,
                            content = note.content,
                            isLoading = false,
                            isWriting = true,
                            imageUri = note.listOfImageUri?.map { it.toUri() } ?: emptyList()
                        )
                    )
                }.also {
                    Log.d("LoadedNote", _uiState.value.toString())
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        noteEditor = it.noteEditor.copy(
                            error = "Failed to load note",
                            isLoading = false
                        )
                    )
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertNote() {
        viewModelScope.launch {
            try {
                val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())

                _uiState.update {
                    it.copy(noteEditor = it.noteEditor.copy(isLoading = true))
                }

                val editor = _uiState.value.noteEditor
                val note = Note(
                    title = editor.title,
                    content = editor.content,
                    date = currentDate,
                    color = randomColor(),
                    listOfImageUri = editor.imageUri.map { it.toString() }
                )
                noteUseCases.addNoteUseCase(note).also {
                    Log.d("Add_Note", note.toString())
                }
                // Reset editor fields
                _uiState.update {
                    it.copy(
                        noteEditor = NoteEditor()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        noteEditor = it.noteEditor.copy(
                            error = "Failed to save note",
                            isLoading = false
                        )
                    )
                }
            }
        }
    }

    fun pinNote(note: Note) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(noteEditor = it.noteEditor.copy(isLoading = true))
                }
                noteUseCases.pinNoteUseCase.invoke(note)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        noteEditor = it.noteEditor.copy(
                            error = e.toString(),
                            isLoading = false
                        )
                    )
                }
            }
        }
    }
}
