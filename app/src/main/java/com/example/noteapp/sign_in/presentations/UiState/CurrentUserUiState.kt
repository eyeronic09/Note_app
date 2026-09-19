package com.example.noteapp.sign_in.presentations.UiState

import com.example.noteapp.sign_in.domain.model.UserData

sealed interface CurrentUserUiState {
    data object Loading : CurrentUserUiState
    data class Success(val userData: UserData) :
        CurrentUserUiState
    data object LoggedOut : CurrentUserUiState
}