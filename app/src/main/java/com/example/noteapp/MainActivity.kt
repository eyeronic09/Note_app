package com.example.noteapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen.AddScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.coreScreen.SealedScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                Column(modifier = Modifier.fillMaxSize()){
                   Appnav()
                }
            }
        }
    }
}



@Composable
fun Appnav() {
    val navController = rememberNavController()

    NavHost(navController , startDestination = SealedScreen.HomeScreen.route ){
        composable(SealedScreen.HomeScreen.route) {
            val vm : HomeScreenViewModel = koinViewModel()
            HomeScreen(viewModel = vm , navController)
        }
        composable(SealedScreen.AddScreen.route) {
            AddScreen(navController = navController)
        }
    }

}