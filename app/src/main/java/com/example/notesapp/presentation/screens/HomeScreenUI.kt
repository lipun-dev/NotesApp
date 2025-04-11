package com.example.notesapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.viewmodel.MyViewMOdel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    viewMOdel: MyViewMOdel = hiltViewModel()
) {

    val state = viewMOdel.noteState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val dialogWidth = 250.dp
    val dialogHeight = 250.dp

    if(showDialog.value){
        BasicAlertDialog(
            onDismissRequest = { showDialog.value = false},

        ){

            Box(modifier = Modifier.size(dialogWidth, dialogHeight)
                        .background(color = Color.White)
            ){
                Column(modifier = Modifier.fillMaxWidth()) {
                    var title = remember { mutableStateOf("") }
                    var content = remember { mutableStateOf("") }



                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = content.value,
                        onValueChange = { content.value = it },
                        label = { Text("Content") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()
                        .align(alignment = Alignment.Start)) {
                        Button(
                            onClick = {
                                showDialog.value = false
                            },
                        ) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = {
                            var notes = Notes(title = title.value, content = content.value)
                            notes.let {
                                viewMOdel.UpsertNotes(it)
                            }

                            showDialog.value = false
                        }) {
                            Text(text = "Confirm")
                        }
                    }

                }

            }

        }
    }

    LaunchedEffect(key1 = Unit) {
        viewMOdel.getAllNotes()
    }
    when{
        state.value.isLoading ->
            CircularProgressIndicator()

        state.value.data.isEmpty() ->
            Scaffold(
                modifier = Modifier.fillMaxSize().padding(15.dp),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "NotesApp")
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {showDialog.value = true}
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)

                    }
                }
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "No Items Are available to show please click on + to add note")
                }

            }
        state.value.data.isNotEmpty()->
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "NotesApp")
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {showDialog.value = true}
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)

                    }
                }
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) {
                    LazyColumn (modifier = Modifier.fillMaxSize()){
                        items (state.value.data){
                            EachNoteUI(title = it.title, content = it.content)
                        }

                    }
                }

            }

    }
}


@Composable
fun EachNoteUI(
     title: String,
     content: String
) {
    Card (modifier = Modifier.fillMaxWidth()){
        Column (modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = title)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = content)
            }
        }

    }
}