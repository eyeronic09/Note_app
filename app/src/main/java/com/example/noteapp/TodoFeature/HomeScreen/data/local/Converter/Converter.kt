package com.example.noteapp.TodoFeature.HomeScreen.data.local.Converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.util.Date


class Converter {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun TimeToString(value : String?) : LocalDateTime?{
        return value?.let {
            LocalDateTime.parse(it)
        }
    }
    @TypeConverter
    fun StringToTime(date : LocalDateTime?) : String?{
        return date?.toString()
    }

}