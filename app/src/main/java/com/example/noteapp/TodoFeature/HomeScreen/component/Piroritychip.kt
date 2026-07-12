package com.example.noteapp.TodoFeature.HomeScreen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.Utilty.chipColor
import com.example.noteapp.TodoFeature.Utilty.chipColorBackGround
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun PriorityChip(todo: Todo) {
    val contentColor = chipColor(todo.priority)
    val backgroundChip = chipColorBackGround(todo.priority)
    Box(
        modifier = Modifier
            .background(color = backgroundChip, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = todo.priority,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = contentColor
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
