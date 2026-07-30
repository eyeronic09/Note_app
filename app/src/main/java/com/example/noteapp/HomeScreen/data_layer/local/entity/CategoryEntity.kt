package com.example.noteapp.HomeScreen.data_layer.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.location.Priority

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val categoryName: String
)