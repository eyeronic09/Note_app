package com.example.noteapp.HomeScreen.coreScreen

sealed class SealedScreen(
    val route : String
) {
    object HomeScreen : SealedScreen("HomeScreen")
    object AddScreen : SealedScreen("AddScreen")

    object View_add_Edit : SealedScreen("view_and_edit/{noteId}"){
        fun createRoute(noteId : Int) = "view_and_edit/$noteId"
    }
}