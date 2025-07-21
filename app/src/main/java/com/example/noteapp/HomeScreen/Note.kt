package com.example.noteapp.HomeScreen

import androidx.compose.ui.graphics.Color

data class Note(
    val id: Int,
    val title:String,
    val content: String ,
    val date: String,
    val color: Color
)

