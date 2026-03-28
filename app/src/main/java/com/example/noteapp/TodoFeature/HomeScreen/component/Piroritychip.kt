package com.example.noteapp.TodoFeature.HomeScreen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.Utilty.chipColor
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun PriorityChip(todo: Todo) {
    val color = chipColor(todo.priority)
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = CircleShape)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = todo.priority,
            color = color,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PriorityChipPreview() {
    PriorityChip(
        todo = Todo(
            title = "Title",
            description = "Description",
            id = 2,
            date = LocalDate.now(),
            time = LocalTime.now(),
            priority = "Low",
            isCompleted = true
        )
    )
}
