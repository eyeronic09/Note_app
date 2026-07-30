package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import androidx.compose.ui.text.toLowerCase
import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNoteUseCase(private val repository: NoteRepository) {
     operator fun invoke(
         noteOrder: NoteOrder = NoteOrder.Title(OrderType.Ascending),
         isArchived: Boolean = false
     ) : Flow<List<Note>> {
        return repository.getNotesNewestFirst().map { notes ->
            val filteredNotes = if (isArchived) {
                notes.filter { it.isArchived }
            } else {
                notes.filterNot { it.isArchived }
            }
            when(noteOrder.orderType){
                OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Date -> filteredNotes.sortedBy { it.date }
                        is NoteOrder.Title -> filteredNotes.sortedBy { it.title }
                        is NoteOrder.Color -> filteredNotes.sortedBy { it.color }
                        is NoteOrder.Pin -> {
                            filteredNotes.sortedByDescending { it.isPin }
                        }
                    }
                }
                OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Date -> filteredNotes.sortedByDescending { it.date }
                        is NoteOrder.Title -> filteredNotes.sortedByDescending { it.title }
                        is NoteOrder.Color -> filteredNotes.sortedByDescending { it.color }
                        is NoteOrder.Pin -> {
                            filteredNotes.sortedByDescending { it.isPin }
                        }
                    }
                }
            }
        }
    }
}

sealed class OrderType {
    object Ascending : OrderType()
    object Descending: OrderType()
}

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)
    class Pin(orderType: OrderType) : NoteOrder(orderType)

    fun copy(orderType: OrderType) : NoteOrder {
        return when(this){
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
            is Pin -> Pin(orderType)
        }
    }
}

