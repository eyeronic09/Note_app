package com.example.noteapp.sign_in.presentations.state

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.sign_in.data.reposistoryImpl.AuthRepositoryImpl
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SignInEvent {
    data object SignInOrSignUp : SignInEvent
    data class EmailChanged(val email: String) : SignInEvent
    data class PasswordChanged(val password: String) : SignInEvent
    data class ContinueWithGoogle(val context: Context) : SignInEvent
}

/**
 * ViewModel responsible for managing the state and actions associated with the sign-in screen.
 *
 * @property repository The repository interface handling authentication operations. Defaults to [AuthRepositoryImpl].
 */
class SignInViewModel(private val repository: AuthReposistory) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())

    init {
        val currentUser = repository.getCurrentUser()
        if (currentUser != null) {
            _uiState.update { it.copy(authState = AuthUiState.Success(currentUser)) }
        }
    }

    /**
     * [StateFlow] representing the current UI state of the sign-in screen.
     */
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    fun onUiEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.ContinueWithGoogle -> {
                signInWithGoogle(event.context)
            }
            is SignInEvent.SignInOrSignUp -> {
                signInOrSignUpEmailPassword()
            }
            is SignInEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }
            is SignInEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }
        }
    }

    private fun signInOrSignUpEmailPassword() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password.trim()

        if (email.isEmpty() || password.isEmpty()) {
            _uiState.update {
                it.copy(authState = AuthUiState.Error("Email and password cannot be empty"))
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(authState = AuthUiState.Loading) }
            val result = repository.signInOrSignUpEmailAndPassword(email, password)
            result.onSuccess { user ->
                _uiState.update { it.copy(authState = AuthUiState.Success(user)) }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(authState = AuthUiState.Error(exception.message ?: "Authentication failed"))
                }
            }
        }
    }

    /**
     * Initiates the Google Sign-In process using the provided context.
     * Updates [uiState]'s [SignInState.authState] to [AuthUiState.Loading] during the process and emits either
     * [AuthUiState.Success] or [AuthUiState.Error] based on the result.
     *
     * @param context The [Context] required to launch the sign-in intent or flow.
     */
    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(authState = AuthUiState.Loading) }
            val result = repository.signInWithGoogle(context)
            result.onSuccess { user ->
                _uiState.update { it.copy(authState = AuthUiState.Success(user)) }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(authState = AuthUiState.Error(exception.message ?: "An unknown error occurred"))
                }
            }
        }
    }
}
