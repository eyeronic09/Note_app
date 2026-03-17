package com.example.noteapp.TodoFeature.HomeScreen.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Todo(
    val id : Int = 0,
    val title : String,
    val description : String ? = null,
    val date : LocalDate,
    val time: LocalDateTime ? = null,
    val priority : String = "Low",
    val isCompleted : Boolean = false
)
