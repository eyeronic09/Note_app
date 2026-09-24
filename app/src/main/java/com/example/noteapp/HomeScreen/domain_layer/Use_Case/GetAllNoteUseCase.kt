package com.example.noteapp.HomeScreen.domain_layer.Use_Case

import com.example.noteapp.HomeScreen.domain_layer.model.Note
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale

class GetAllNoteUseCase(private val repository: NoteRepository) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Title(OrderType.Ascending),
        isArchived: Boolean = false
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Date -> notes.sortedWith(compareBy<Note> { it.updatedAt }.thenBy { it.id })
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase(Locale.getDefault()) }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Pin -> notes.sortedByDescending { it.isPin }
                    }
                }
                OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Date -> notes.sortedWith(compareByDescending<Note> { it.updatedAt }.thenByDescending { it.id })
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase(Locale.getDefault()) }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        is NoteOrder.Pin -> notes.sortedByDescending { it.isPin }
                    }
                }
            }
        }
    }
}

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)
    class Pin(orderType: OrderType) : NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
            is Pin -> Pin(orderType)
        }
    }
}
