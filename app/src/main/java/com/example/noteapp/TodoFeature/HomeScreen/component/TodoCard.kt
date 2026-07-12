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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.TodoFeature.AddScreen._EditScreen
import com.example.noteapp.TodoFeature.AddScreen.todoCreationEvent
import com.example.noteapp.TodoFeature.HomeScreen.domain.model.Todo
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreenEvent
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoCard(
    todo: Todo,
    onAction: (TodoHomeScreenEvent) -> Unit,
    onEdit: (todoCreationEvent) -> Unit,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            onEdit(todoCreationEvent.TakeTodoId(todo.id))
            navigator.push(_EditScreen(todo.id))
        },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = {
                    onAction(TodoHomeScreenEvent.OnToggleCompleted(todo))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    color = if (todo.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!todo.description.isNullOrBlank()) {
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = todo.time?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "No time",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    PriorityChip(todo)
                }
            }

            Row {
                IconButton(onClick = {
                    onAction(TodoHomeScreenEvent.DeleteTodoHomeScreen(todo))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun TodoCardPreview() {
//    TodoCard(
//        todo = Todo(
//            title = "Title",
//            description = "Description",
//            id = 2,
//            date = LocalDate.now(),
//            time = LocalTime.now(),
//            priority = "Low",
//            isCompleted = true
//        ),
//        onAction = {},
//        navigator = ,
//        modifier = TODO()
//    )
//}