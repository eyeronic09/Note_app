package com.example.noteapp.HomeScreen

sealed class Screen (val route: String) {
    object Home: Screen("home")
    object EditorScreen: Screen("EditorScreen")
}