package com.example.noteapp.TodoFeature.Utilty

import android.R
import androidx.compose.ui.graphics.Color


fun chipColor(text: String): Color {
    return when (text) {
        "Low" -> Color(0xFF4CAF50)
        "Medium" -> Color(0xFFFFC107)
        "High" -> Color(0xFFF44336)
        else -> Color.Gray
    }
}
fun chipColorBackGround(text: String): Color {
    return when (text) {
        "Low" -> Color(0xFFE8F5E9)
        "Medium" -> Color(0xFFFFF8E1)
        "High" -> Color(0xFFFFEBEE)
        else -> Color(0xFFF5F5F5)
    }
}
