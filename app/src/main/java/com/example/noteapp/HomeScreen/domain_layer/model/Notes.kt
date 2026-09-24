package com.example.noteapp.HomeScreen.domain_layer.model

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val firebaseUserId: String = "",
    val date: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val color: Int,
    val listOfImageUri: List<String>? = emptyList(),
    val isPin : Boolean = false,
    val isArchived : Boolean = false,
    val category : String? = null,
    val syncedStatus : Boolean = false
)
