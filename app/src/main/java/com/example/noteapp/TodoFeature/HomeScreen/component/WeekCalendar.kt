package com.example.noteapp.TodoFeature.HomeScreen.component


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.HomeScreenUiState
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreen
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreenEvent
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun previewWeekCalendarHomeScreen() {
    WeekCalendarHomeScreen(
        Uistate = HomeScreenUiState(
            todayDate = LocalDate.now(),
            todo = listOf(
                Todo(
                    id = 1,
                    title = "Gym",
                    description = "Leg day",
                    date = LocalDate.now(),
                    priority = "High"
                ),
                Todo(
                    id = 2,
                    title = "Code",
                    description = "Project Note App",
                    date = LocalDate.now(),
                    priority = "Medium"
                ),
                Todo(
                    id = 3,
                    title = "Read",
                    description = "Atomic Habits",
                    date = LocalDate.now().plusDays(1),
                    priority = "Low"
                )
            )
        ),
        onAction = {} // Empty lambda for preview
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCalendarHomeScreen(Uistate : HomeScreenUiState , onAction: (TodoHomeScreenEvent) -> Unit) {
    val currentDate = Uistate.todayDate
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate ,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )
    Column(modifier = Modifier.fillMaxSize()) {
        WeekCalendar(
            state = state,
            dayContent = { day ->
                Log.d("todayDay" , day.date.toString())
                val textColor = if (day.date == currentDate) {
                    Color.Green
                } else {
                    Color.Gray
                }
                Box(
                    modifier = Modifier
                        .aspectRatio(1f) // Makes the day cell a square
                        .clickable {
                            onAction(TodoHomeScreenEvent.OnSpecificDate(day))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        color = textColor
                    )

                }
            },

        )

        LazyColumn() {
            items(Uistate.todo){ it ->
                Text(it.title.toString())
            }
        }
    }
}
