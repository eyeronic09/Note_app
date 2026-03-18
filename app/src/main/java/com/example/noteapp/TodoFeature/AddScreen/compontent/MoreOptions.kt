package com.example.noteapp.TodoFeature.AddScreen.compontent

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.TodoFeature.AddScreen.TodoAddScreenUiState
import com.example.noteapp.TodoFeature.AddScreen.todoCreationEvent
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreOptionMenu(state: TodoAddScreenUiState, onAction:(todoCreationEvent) -> Unit ){
    var selectDate by remember {mutableStateOf(value = false)  }
    var selectTime by remember {mutableStateOf(value = false)  }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { selectDate = true }) {
                Text("Selected Date")
            }
            if (state.date == null) {
                Text("Not Selected")
            } else {
                state.date.let { date ->
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    Text(text = date.format(formatter))
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(onClick = {
                selectTime = true
            }) {
                Text("Select Time")
            }
            if (state.time == null) {
                Text("Not Selected")
            }else {
                state.time.let { time ->
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    Text(text = time.format(formatter))
                }
            }
        }

    }
    if (selectDate) {
        DatePickerPopup(
            onDateSelected = { it ->
                onAction(todoCreationEvent.SetDate(it))
                Log.d("date", it.toString())
                selectDate = false
            },
            onDismiss = {
                selectDate = false
            }
        )
    }
    if (selectTime) {
        TimePickerPopup(
            onTimeSelected = { it ->
                onAction(
                    todoCreationEvent.SetTime(
                        it
                    )
                )
                Log.d("time" , it.toString())
            },
            onDismiss = {
                selectTime = false
            },
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun or() {
    MoreOptionMenu(
        state = TodoAddScreenUiState(), {}
    )
}