package com.example.noteapp.TodoFeature.AddScreen.compontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.TodoFeature.AddScreen.Prioritise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritySelecterSegmentButton(
    options: Prioritise,
    onPrioritySelected: (Prioritise) -> Unit,
    modifier: Modifier = Modifier
) {
    val priorities = Prioritise.entries
    
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Priority",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            priorities.forEachIndexed { index, priority ->
                SegmentedButton(
                    selected = options == priority,
                    onClick = { onPrioritySelected(priority) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = priorities.size
                    ),
                    label = {
                        Text(priority.name)
                    }
                )
            }
        }
    }
}
