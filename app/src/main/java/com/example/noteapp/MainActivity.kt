package com.example.noteapp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.WindowInsets
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.NoteTab
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoTab
import com.example.noteapp.ui.theme.NoteAppTheme
import android.Manifest
import androidx.compose.material3.Button
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted : Boolean ->
        if (isGranted) {

            Toast.makeText(this, "Notification permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notification permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        checkAndRequestNotificationPermission()
        super.onCreate(savedInstanceState)
        

        
        setContent {



            NoteAppTheme {
                TabNavigator(NoteTab) { _ ->
                    Scaffold(
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        bottomBar = {
                            NavigationBar {
                                TabNavigationItem(NoteTab)
                                TabNavigationItem(TodoTab)
                            }
                        },
                        content = { padding ->
                            CurrentTabContent(padding)
                        }
                    )
                }
            }
        }
    }



    @Composable
    private fun CurrentTabContent(padding: PaddingValues) {
        Box(Modifier.padding(padding)) {
            CurrentTab()
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title) } },
            label = { Text(tab.options.title) }
        )
    }
    fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ - Need to request permission
            val status = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if (status != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Android 12 and below - Permission automatically granted
            Toast.makeText(this, "Notifications enabled (Android ${Build.VERSION.SDK_INT})", Toast.LENGTH_SHORT).show()
        }
    }

}
