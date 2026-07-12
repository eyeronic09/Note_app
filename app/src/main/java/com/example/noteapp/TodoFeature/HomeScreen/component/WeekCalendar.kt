package com.example.noteapp.TodoFeature.HomeScreen.component


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.HomeScreenUiState
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreenEvent
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale

//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//private fun previewWeekCalendarHomeScreen() {
//    WeekCalendarHomeScreen(
//        Uistate = HomeScreenUiState(
//            todayDate = LocalDate.now(),
//            todo = listOf(
//                Todo(
//                    id = 1,
//                    title = "Gym",
//                    description = "Leg day",
//                    date = LocalDate.now(),
//                    priority = "High"
//                ),
//                Todo(
//                    id = 2,
//                    title = "Code",
//                    description = "Project Note App",
//                    date = LocalDate.now(),
//                    priority = "Medium"
//                ),
//                Todo(
//                    id = 3,
//                    title = "Read",
//                    description = "Atomic Habits",
//                    date = LocalDate.now().plusDays(1),
//                    priority = "Low"
//                )
//            )
//        ),
//        onAction = {},
//
//    )
//}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCalendarHomeScreen(uiState : HomeScreenUiState, onAction: (TodoHomeScreenEvent) -> Unit, navigator: Navigator) {
    val currentDate = uiState.todayDate
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val selectedDate = uiState.selectedDate
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate ,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        WeekCalendar(
            modifier = Modifier.padding(vertical = 8.dp),
            state = state,
            dayContent = { day ->
                val isSelected = selectedDate != null && day.date == selectedDate
                val isToday = day.date == currentDate
                
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(
                            color = when {
                                isSelected -> MaterialTheme.colorScheme.primary
                                isToday -> MaterialTheme.colorScheme.secondaryContainer
                                else -> Color.Transparent
                            },
                            shape = CircleShape
                        )
                        .clickable {
                            onAction(TodoHomeScreenEvent.OnSpecificDate(day))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = day.date.dayOfWeek.name.take(1),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = day.date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
        )
    }
}
