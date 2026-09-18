package com.example.noteapp.sign_in.domain.reposistory

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface AuthReposistory {
    fun getCurrentUserId() : String?
    fun isUserisCurrentlyloggedIN() : Boolean
    suspend fun signInWithGoogle(
        context: Context
    ): Result<FirebaseUser>

    suspend fun signOut() : Unit

    suspend fun signInOrSignUpEmailAndPassword(email: String, password: String): Result<FirebaseUser>
}
