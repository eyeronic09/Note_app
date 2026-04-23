package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import androidx.core.net.toUri

class OpenThePhoto(private val ImageToOpen: String) : Screen {

    @Composable
    override fun Content() {
        val uriImage = ImageToOpen.toUri()
        OpenPhotoContent(uriImage)
    }

}
@Composable
fun OpenPhotoContent(uri: Uri) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = uri,
            contentDescription = "Opened Photo",
            contentScale = ContentScale.Fit
        )
    }
}
