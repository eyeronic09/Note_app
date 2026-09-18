package com.example.noteapp.sign_in.presentations.state

import com.google.firebase.auth.FirebaseUser

data class SignInState(
    val email: String = "",
    val password: String = "",
    val authState: AuthUiState = AuthUiState.Idle,
    val userIsAlreadyLoggedIn: Boolean = false
)

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Success(val user: FirebaseUser? = null) : AuthUiState
    data class Error(val message: String) : AuthUiState
}
