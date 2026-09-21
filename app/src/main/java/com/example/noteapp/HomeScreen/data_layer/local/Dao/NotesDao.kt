package com.example.noteapp.HomeScreen.data_layer.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.noteapp.HomeScreen.data_layer.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes WHERE (isArchived = :isArchived OR (:isArchived = 0 AND isArchived IS NULL)) ORDER BY id ASC")
    fun getNotesOldestFirst(isArchived: Boolean = false): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE (isArchived = :isArchived OR (:isArchived = 0 AND isArchived IS NULL)) ORDER BY id DESC")
    fun getNotesNewestFirst(isArchived: Boolean = false): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteEntity

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    suspend fun searchNotes(query: String): List<NoteEntity>

    @Upsert
    suspend fun addNotes(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNotes(noteEntity: NoteEntity)

    @Update
    suspend fun updateNotes(noteEntity: NoteEntity)
}
