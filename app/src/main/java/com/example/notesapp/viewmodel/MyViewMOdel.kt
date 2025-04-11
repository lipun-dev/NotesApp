package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.repo.repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewMOdel @Inject constructor(val repo: repo) : ViewModel(){

    private val _noteState = MutableStateFlow(AppState())
    val noteState = _noteState.asStateFlow()
    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllNotes().collect {
               _noteState.value = AppState(data = it)
            }
        }
    }




    fun UpsertNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repo.UpsertNotes(notes)
    }
    fun DeleteNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repo.DeleteNotes(notes)
    }



}



data class AppState(
    val isLoading: Boolean = false,
    val data: List<Notes> = emptyList()
)