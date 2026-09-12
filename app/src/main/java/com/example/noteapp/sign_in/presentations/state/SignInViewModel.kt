package com.example.noteapp.sign_in.presentations.state

import androidx.lifecycle.ViewModel
import com.example.noteapp.sign_in.domain.model.SignInResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel() : ViewModel()   {


    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }
    fun resetState() {
        _state.update {
            SignInState()
        }
    }

}