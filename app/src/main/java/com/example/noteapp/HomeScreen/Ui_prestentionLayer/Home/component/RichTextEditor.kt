package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.FormatStrikethrough
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenEvent
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenUIState
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichNoteEditor(
    state: HomeScreenUIState,
    onAction: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val richTextState = rememberRichTextState()

    // Load HTML content into RichTextState when note content is loaded
    LaunchedEffect(state.noteEditor.content) {
        if (richTextState.toHtml() != state.noteEditor.content) {
            richTextState.setHtml(state.noteEditor.content)
        }
    }

    // Sync edited HTML back to ViewModel
    LaunchedEffect(richTextState.annotatedString) {
        val newHtml = richTextState.toHtml()
        if (newHtml != state.noteEditor.content) {
            onAction(HomeScreenEvent.UpdateContent(newHtml))
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Show Toolbar only in Writing Mode
        if (state.noteEditor.isWriting) {
            RichTextToolbar(
                richTextState = richTextState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        // Rich Text Input Field
        OutlinedRichTextEditor(
            state = richTextState,
            enabled = state.noteEditor.isWriting,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = { Text("Note Content") },
            colors = RichTextEditorDefaults.outlinedRichTextEditorColors()
        )
    }
}

@Composable
fun RichTextToolbar(
    richTextState: RichTextState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Bold Button
        IconButton(
            onClick = {
                richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (richTextState.currentSpanStyle.fontWeight == FontWeight.Bold)
                    MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            )
        ) {
            Icon(Icons.Default.FormatBold, contentDescription = "Bold")
        }

        // Italic Button
        IconButton(
            onClick = {
                richTextState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (richTextState.currentSpanStyle.fontStyle == FontStyle.Italic)
                    MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            )
        ) {
            Icon(Icons.Default.FormatItalic, contentDescription = "Italic")
        }

        // Underline Button
        IconButton(
            onClick = {
                richTextState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (richTextState.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true)
                    MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            )
        ) {
            Icon(Icons.Default.FormatUnderlined, contentDescription = "Underline")
        }

        // Strikethrough Button
        IconButton(
            onClick = {
                richTextState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (richTextState.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true)
                    MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            )
        ) {
            Icon(Icons.Default.FormatStrikethrough, contentDescription = "Strikethrough")
        }

        // Heading Button
        IconButton(
            onClick = {
                richTextState.toggleSpanStyle(
                    SpanStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        ) {
            Icon(Icons.Default.Title, contentDescription = "Header")
        }

        // Bullet List Button
        IconButton(
            onClick = {
                richTextState.toggleUnorderedList()
            }
        ) {
            Icon(Icons.AutoMirrored.Filled.FormatListBulleted, contentDescription = "Bullet List")
        }

        // Ordered List Button
        IconButton(
            onClick = {
                richTextState.toggleOrderedList()
            }
        ) {
            Icon(Icons.Default.FormatListNumbered, contentDescription = "Ordered List")
        }
    }
}
