package com.example.noteapp.HomeScreen.data_layer.local.database

import androidx.room.TypeConverter

class NoteConverters {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let {
            if (it.isEmpty()) emptyList() else it.split(",")
        }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(",")
    }
}
