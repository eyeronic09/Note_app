package com.example.noteapp.sign_in.remote.reposistoryImpl

import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import com.google.firebase.auth.FirebaseAuth

class AuthReposistoryImpl(private val auth: FirebaseAuth) : AuthReposistory {
    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}