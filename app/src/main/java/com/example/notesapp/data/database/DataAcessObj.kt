package com.example.notesapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notesapp.data.entity.Notes
import kotlinx.coroutines.flow.Flow


@Dao
interface DataAcessObj {

    @Upsert
    suspend fun UpsertNotes(notes: Notes)

    @Delete
    suspend fun DeleteNotes(notes: Notes)

    @Query("Select * from notes_table")
    fun getAllNotes(): Flow<List<Notes>>
}