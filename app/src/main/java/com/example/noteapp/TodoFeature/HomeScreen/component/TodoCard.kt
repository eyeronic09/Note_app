package com.example.noteapp.TodoFeature.HomeScreen.component



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreenEvent
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    todo: Todo,
    onAction: (TodoHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = {} ,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),

    ) {
        // Main layout inside the card is a Row
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: an icon
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = {}
            )

            // Space between icon and text
            Spacer(modifier = Modifier.width(16.dp))

            // Right side: a Column for text content
            Column {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = todo.description.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = todo.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                )
                Row(modifier = Modifier.fillMaxWidth().padding( 8.dp),
                    horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    PriorityChip(todo)
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TodoCardPreview() {
    TodoCard(
        todo = Todo(
            title = "Title",
            description = "Description",
            id = 2,
            date = LocalDate.now(),
            time = LocalTime.now(),
            priority = "Low",
            isCompleted = true
        ),
        onAction = {}
    )
}