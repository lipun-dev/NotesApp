package com.example.notesapp.repo

import com.example.notesapp.data.database.DataAcessObj
import com.example.notesapp.data.entity.Notes
import javax.inject.Inject

class repo @Inject constructor(val dao: DataAcessObj){

    suspend fun UpsertNotes(notes: Notes) = dao.UpsertNotes(notes)

    suspend fun DeleteNotes(notes: Notes) = dao.DeleteNotes(notes)

    fun getAllNotes() = dao.getAllNotes()



}