package com.example.noteapp.HomeScreen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun noteDisplay(modifier: Modifier = Modifier, mainViewModel: MainViewModel, navController: NavController) {
       val context = LocalContext.current
       val  noteList by mainViewModel.notes.collectAsState()
    val cardColors = remember { mutableStateListOf<Color>() }
        Column(modifier = modifier) {
        if (noteList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){Text(
                text = "No notes yet. Click the '+' button to add one!",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            }
        } else {
            LazyColumn {
                items(
                    noteList
                ) { note ->
                    Card(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .fillMaxWidth()
                            .clickable(onClick = {
                                navController.navigate("${Screen.EditorScreen.route}/${note.id}")
                            }),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = note.color)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleLarge
                            )
                            if (note.content.isNotBlank()) {
                                Text(
                                    text = note.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 3
                                )
                            }
                            HorizontalDivider(thickness = 2.dp)
                            Row(modifier = Modifier.padding(top = 8.dp) , verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = "Created On"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    modifier = Modifier.padding(end = 8.dp),
                                    text ="Created On: ${note.date}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    IconButton(
                                        onClick = {
                                            val textToCopy = "Title: ${note.title}\nContent: ${note.content}"
                                            val clipboardIntent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(Intent.EXTRA_TEXT, textToCopy)
                                            }
                                            context.startActivity(Intent.createChooser(clipboardIntent, "Copy Note"))
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Share,
                                            contentDescription = "Created On"
                                        )
                                    }
                                    IconButton(onClick = {mainViewModel.deleteNote(note)}) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Created On"
                                        )
                                    }
                                    Log.d("HomeScreen", ": id=${note.id}, title=${note.title}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
