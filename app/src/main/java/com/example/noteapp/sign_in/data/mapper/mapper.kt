package com.example.noteapp.sign_in.data.mapper

import com.example.noteapp.sign_in.domain.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.rpc.context.AttributeContext

object Authmapper {
    fun FirebaseUser.toUserData(): UserData = UserData(
        userId = uid,
        username = displayName ?: email
    )
}