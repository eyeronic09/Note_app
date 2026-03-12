package com.example.noteapp.TodoFeature.HomeScreen.domain.model

data class Todo(
    val id : Int = 0 ,
    val title : String,
    val content : String,
    val date : String,
    val deadlineTimestamp: String ? = null ,
    val priority : String = "Low",
    val isCompleted : Boolean = false
)
