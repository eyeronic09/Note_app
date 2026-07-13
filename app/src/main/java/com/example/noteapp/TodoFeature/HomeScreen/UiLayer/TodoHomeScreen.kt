package com.example.noteapp.TodoFeature.HomeScreen.UiLayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteapp.TodoFeature.HomeScreen.component.TodoCard
import com.example.noteapp.TodoFeature.HomeScreen.component.WeekCalendarHomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoHomeScreen(
    state: HomeScreenUiState,
    onAction: (TodoHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    navigator: Navigator
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header Section moved here to be closer to top
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 8.dp)
        ) {
            Text(
                "My Tasks",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                state.todayDate.toString(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
        }

        // Stats Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCard(
                title = "To Do",
                count = state.todo.filter { !it.isCompleted }.size.toString(),
                icon = Icons.Default.CalendarMonth,
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                modifier = Modifier.weight(1f)
            )
            StatusCard(
                title = "Done",
                count = state.todo.filter { it.isCompleted }.size.toString(),
                icon = Icons.Default.CheckCircle,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
                modifier = Modifier.weight(1f)
            )
        }

        // Calendar Section
        WeekCalendarHomeScreen(uiState = state, onAction = onAction, navigator = navigator)

        // Tasks List
        Text(
            "Tasks",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        if (state.todo.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No tasks for this day",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp, start = 4.dp, end = 4.dp)
            ) {
                items(state.todo) { todo ->
                    TodoCard(
                        todo = todo,
                        onAction = onAction,
                        navigator = navigator,
                        onEdit = {

                        },
                    )
                }
            }
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    count: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.labelMedium)
                Text(text = count, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
