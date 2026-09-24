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

    @Query("""
        SELECT * FROM notes
        WHERE firebaseUserId = :userId
        ORDER BY updatedAt DESC
    """)
    fun getNotesNewestFirst(
        userId: String
    ): Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM notes
        WHERE id = :noteId
    """)
    suspend fun getNoteById(
        noteId: String
    ): NoteEntity

    @Query("""
        SELECT * FROM notes
        WHERE firebaseUserId = :userId
        AND (
            title LIKE '%' || :query || '%'
            OR content LIKE '%' || :query || '%'
        )
    """)
    suspend fun searchNotes(
        query: String,
        userId: String
    ): List<NoteEntity>

    @Upsert
    suspend fun upsertNote(
        noteEntity: NoteEntity
    )

    @Delete
    suspend fun deleteNote(
        noteEntity: NoteEntity
    )

    @Update
    suspend fun updateNote(
        noteEntity: NoteEntity
    )
}