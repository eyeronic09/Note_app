package com.example.noteapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.AddScreen.AddScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.EditAndViewScreen.ViewAndEditScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreen
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.coreScreen.SealedScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.viewmodel.factory.KoinViewModelFactory

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
        Log.d("NavGraph" , this.toString())
        composable(SealedScreen.HomeScreen.route) {
            val vm : HomeScreenViewModel = koinViewModel()
            HomeScreen(viewModel = vm , navController)
        }
        composable(SealedScreen.AddScreen.route) {
            AddScreen(navController = navController)
        }
        composable(
            route = SealedScreen.View_add_Edit.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            val viewModel: HomeScreenViewModel = koinViewModel()
            
            // Load the note when the screen is first shown
            LaunchedEffect(Unit) {
                viewModel.onEvent(HomeScreenEvent.OpenToReadAndUpdate(noteId))
            }
            
            ViewAndEditScreen(
                navController = navController,
                viewModel = viewModel,
                onClickNavigate = { navController.navigateUp() }
            )
        }
    }

}