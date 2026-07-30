package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.Ui_prestentionLayer.ArchiveScreen.ArchiverEvent

data class NoteUseCases(
    val getAllNoteUseCase: GetAllNoteUseCase,
    val getAllArchiverUseCase: GetAllArchiverUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val pinNoteUseCase: PinNoteUseCase,
    val unArchiverUseCases: UnArchiverUseCase
)
