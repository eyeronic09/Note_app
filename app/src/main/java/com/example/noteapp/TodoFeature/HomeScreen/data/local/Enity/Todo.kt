package com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("todo")
data class TodoEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,
    val title : String,
    val content : String ?= null,
    val date : String,
    val deadlineTimestamp: String ? = null ,
    val priority : String = "Low",
    val isCompleted : Boolean = false
)