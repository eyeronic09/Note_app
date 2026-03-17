package com.example.noteapp.TodoFeature.HomeScreen.data.local.Enity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity("todo")
data class TodoEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,
    val title : String,
    val description : String ?= null,
    val date : LocalDate,
    val time: LocalDateTime ? = null ,
    val priority : String = "Low",
    val isCompleted : Boolean = false
)