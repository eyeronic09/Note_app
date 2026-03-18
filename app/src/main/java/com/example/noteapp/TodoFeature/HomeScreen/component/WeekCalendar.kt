package com.example.noteapp.TodoFeature.HomeScreen.component


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.HomeScreenUiState
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.WeekDayPosition  // If this exists

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCalendarHomeScreen(state : HomeScreenUiState) {
    val currentDate = state.todayDate
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate ,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )
    WeekCalendar(
        state = state,
        dayContent = {day ->
            val textColor = if (day.date == currentDate) {
                Color.Green // The date is within the week range
            } else {
                Color.Gray  // Usually not hit in WeekCalendar, but good for safety
            }

            Box(
                modifier = Modifier
                    .aspectRatio(1f) // Makes the day cell a square
                    .clickable { /* Handle click */ },
                contentAlignment = Alignment.Center
            ) {
               Text(
                   text = day.date.dayOfMonth.toString(),
                   color = textColor
               )

            }
        }

    )


}