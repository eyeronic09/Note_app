package com.example.noteapp.TodoFeature.HomeScreen.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Todo(
    val id : Int = 0,
    val title : String,
    val description : String ? = null,
    val date : LocalDate,
    val time: LocalTime ? = null,
    val priority : String = "Low",
    val isCompleted : Boolean = false
)
