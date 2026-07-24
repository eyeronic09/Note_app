package com.example.noteapp.HomeScreen.domain_layer.Use_Case

data class NoteUseCases(
    val getAllNoteUseCase: GetAllNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val pinNoteUseCase: PinNoteUseCase
)
