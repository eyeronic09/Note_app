package com.example.noteapp.sign_in.presentations.state

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import com.example.noteapp.sign_in.data.reposistoryImpl.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the state and actions associated with the sign-in screen.
 *
 * @property repository The repository interface handling authentication operations. Defaults to [AuthRepositoryImpl].
 */
class SignInViewModel(private val repository: AuthReposistory = AuthRepositoryImpl()) : ViewModel()   {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)

    /**
     * [StateFlow] representing the current authentication state of the UI.
     */
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        if (repository.isUserisCurrentlyloggedIN()) {
           _uiState.value = AuthUiState.Idle 
        } else {
           _uiState.value = AuthUiState.Idle
        }
    }

    /**
     * Initiates the Google Sign-In process using the provided context.
     * Updates [uiState] to [AuthUiState.Loading] during the process and emits either
     * [AuthUiState.Success] or [AuthUiState.Error] based on the result.
     *
     * @param context The [Context] required to launch the sign-in intent or flow.
     */
    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.signInWithGoogle(context)
            result.onSuccess { user ->
                _uiState.value = AuthUiState.Success(user)
            }.onFailure { exception ->
                _uiState.value = AuthUiState.Error(exception.message ?: "An unknown error occurred")
            }
        }
    }
}

