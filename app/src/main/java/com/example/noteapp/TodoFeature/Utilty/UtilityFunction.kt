package com.example.noteapp.TodoFeature.Utilty

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
