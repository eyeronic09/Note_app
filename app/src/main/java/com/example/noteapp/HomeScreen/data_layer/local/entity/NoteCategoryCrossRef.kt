package com.example.noteapp.HomeScreen.data_layer.local.entity

import androidx.room.Entity
import androidx.room.Index


@Entity(primaryKeys = ["id" , "categoryId"] , indices = [Index("id" , "categoryId")])
data class NoteCategoryCrossRef(
    val noteid : Int , val categoryId: Int
)
