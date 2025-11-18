package com.example.noteapp.HomeScreen.domain_layer.model

import androidx.compose.ui.graphics.Color
import androidx.room.PrimaryKey

data class Note(
    val id: Int ,
    val title: String,
    val content: String,
    val date: String
)