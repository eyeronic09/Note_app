package com.example.noteapp.HomeScreen.Ui_prestentionLayer.ArchiveScreen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.viewmodel.koinViewModel

class ArchiverScreen : Screen {
    @Composable
    override fun Content() {
        ArchiverScreenRoute()
    }

}
@Composable
fun ArchiverScreenRoute(viewModel : ArchiverScreenViewModel = koinViewModel()) {

}