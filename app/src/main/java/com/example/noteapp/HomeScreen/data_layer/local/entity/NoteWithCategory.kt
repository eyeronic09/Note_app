package com.example.noteapp.HomeScreen.data_layer.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.noteapp.HomeScreen.domain_layer.model.Note

data class CategoryWithNote(
    @Embedded val category : CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId",
        associateBy = Junction(NoteCategoryCrossRef::class)
    )
    val note : List<NoteEntity>
)
