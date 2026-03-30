package com.example.noteapp.TodoFeature.Utilty

import android.R
import androidx.compose.ui.graphics.Color


fun chipColor(text: String): Color {
    when (text) {
        "Low" -> {
            return Color.Green
        }
        "Medium" -> {
            return Color.Yellow
        }
        "High" -> {
            return Color.Red
        }
    }
    return Color.Unspecified
}
fun chipColorBackGround(text: String): Color {
    when (text) {
        "Low" -> {
            return Color(red =128   , green = 239 , blue = 128 , alpha = 255  )
        }
        "Medium" -> {
             return Color(red = 237, green = 239, blue = 128, alpha = 255)
        }
        "High" -> {
            return Color(red = 239, green = 128, blue = 128, alpha = 255)
        }
    }
    return Color.Unspecified
}
