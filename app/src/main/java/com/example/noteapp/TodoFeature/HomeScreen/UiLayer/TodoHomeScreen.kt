package com.example.noteapp.TodoFeature.HomeScreen.UiLayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteapp.TodoFeature.HomeScreen.component.WeekCalendarHomeScreen
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun _TodoAddScreen(
    state : HomeScreenUiState,
    onAction:(TodoHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    navigator: Navigator
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {Text("Todo")}
            )
        },
    ){ paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            // WeekCalendar as separate component outside LazyColumn
            WeekCalendarHomeScreen(Uistate = state)

            // LazyColumn for todo items only
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.todo) { todo ->
                    Text(todo.title)
                }
            }
        }
    }
}