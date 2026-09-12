package com.example.noteapp.sign_in.presentations.state

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)
