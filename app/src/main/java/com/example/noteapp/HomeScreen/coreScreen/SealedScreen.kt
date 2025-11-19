package com.example.noteapp.HomeScreen.coreScreen

sealed class SealedScreen(
    val route : String
) {
    object HomeScreen : SealedScreen("HomeScreen")
    object AddScreen : SealedScreen("AddScreen")
}