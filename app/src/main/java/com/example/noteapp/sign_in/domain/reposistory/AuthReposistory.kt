package com.example.noteapp.sign_in.domain.reposistory

interface AuthReposistory {
    fun getCurrentUserId() : String?
}