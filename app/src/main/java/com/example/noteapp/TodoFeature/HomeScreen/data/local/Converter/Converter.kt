package com.example.noteapp.TodoFeature.HomeScreen.data.local.Converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class Converter {
    // LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun toDate(date: LocalDate?): String? = date?.toString()

    // LocalTime
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun toTime(time: LocalTime?): String? = time?.toString()

    // LocalDateTime
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    fun toDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

}