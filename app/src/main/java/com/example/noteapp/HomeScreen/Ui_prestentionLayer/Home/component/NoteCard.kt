package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.HomeScreen.domain_layer.model.Note

@Composable
fun NoteCard(
    note: Note,
    onNoteClick: () -> Unit
) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable(onClick = onNoteClick),
        colors = CardDefaults.cardColors(containerColor = Color.Red)

    )
    {
        Column(modifier = Modifier.padding(16.dp).background(Color(note.color))) {
            Text(text = note.title, style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = note.content)
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun NoteCardPreview() {
   val note = Note(
        id = 1,
        title = "Kotlin lecture notes",
        content = "Kotlin is statically type Programming lang with OOPs concepts",
        date = "1-1-2025" ,
       color = 21
    )
    NoteCard(
        note = note,
        onNoteClick = {}
    )
}