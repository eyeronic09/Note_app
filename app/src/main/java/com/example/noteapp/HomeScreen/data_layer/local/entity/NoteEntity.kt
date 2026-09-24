package com.example.noteapp.HomeScreen.data_layer.local.entity

import android.graphics.Color.WHITE
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val firebaseUserId: String = "",
    val date: String = "",
    val updatedAt: Long = System.currentTimeMillis(),
    val color : Int = WHITE,
    val listOfImageUri : List<String>? = emptyList(),
    val isPin : Boolean = false,
    val isArchived : Boolean = false,
    val category : String? = null,
    val syncedStatus : Boolean = false
)
