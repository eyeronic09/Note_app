package com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.NoteOrder
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.OrderType

@Composable
fun DefaultRadioButton(
    text : String,
    selected: Boolean,
    onSelected : () -> Unit ,
    modifier : Modifier = Modifier

){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.onPrimary,
                unselectedColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder : NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange : (NoteOrder) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()){
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelected = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelected = {
                    onOrderChange(NoteOrder.Color(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelected = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DefaultRadioButton(
            text = "Ascending",
            selected = noteOrder.orderType is OrderType.Ascending,
            onSelected = {
                onOrderChange(noteOrder.copy(OrderType.Ascending))
            },

        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultRadioButton(
            text = "Descending",
            selected = noteOrder.orderType is OrderType.Descending,
            onSelected = {
                onOrderChange(noteOrder.copy(OrderType.Descending))
            }
        )
    }

}