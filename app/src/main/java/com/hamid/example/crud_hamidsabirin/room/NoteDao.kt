package com.hamid.example.crud_hamidsabirin.room

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: Note)
    @Update
    suspend fun updateNote(update: Note)
    @Delete
    suspend fun deleteNote(delete: Note)
    @Query("SELECT * FROM note")
    suspend fun getNotes(): List<Note>
    @Query("SELECT * FROM note WHERE id=:note_id")
    suspend fun getNote(note_id: Int): List<Note>
}