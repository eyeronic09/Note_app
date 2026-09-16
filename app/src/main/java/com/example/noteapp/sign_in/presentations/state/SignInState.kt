package com.example.noteapp.sign_in.presentations.state

import com.google.firebase.auth.FirebaseUser


sealed interface AuthUiState {
    /**
     * Represents the initial or idle state when no authentication operation is ongoing.
     */
    data object Idle : AuthUiState

    /**
     * Represents the state when an authentication operation is in progress.
     */
    data object Loading : AuthUiState

    /**
     * Represents a successful authentication state containing the authenticated [FirebaseUser].
     *
     * @property user The successfully authenticated [FirebaseUser].
     */
    data class Success(val user: FirebaseUser) : AuthUiState

    /**
     * Represents a failed authentication state containing an error message.
     *
     * @property message The detailed error message explaining why authentication failed.
     */
    data class Error(val message: String) : AuthUiState
}