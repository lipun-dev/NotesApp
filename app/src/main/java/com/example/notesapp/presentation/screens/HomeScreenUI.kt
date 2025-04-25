package com.example.notesapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val dialogWidth = 350.dp
    val dialogHeight =300.dp
    var showerror = remember { mutableStateOf(false) }



    if(showDialog.value){
        BasicAlertDialog(
            onDismissRequest = { showDialog.value = false},
            modifier = Modifier.wrapContentHeight()

        ){
//inside the dialogue box functions
            Box(modifier = Modifier
                .fillMaxWidth()
                .size(dialogWidth, dialogHeight)
                .wrapContentHeight()
                .background(color = Color.White)
            ){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())) {
                    var title = remember { mutableStateOf("") }
                    var content = remember { mutableStateOf("") }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        //heading of the dialogue box
                        Text(text = "ADD NOTES",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 20.sp
                            ))
                    }
                    //title field of the dialogue box
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it
                            if (it.isNotBlank()) {
                                showerror.value = false
                            }},
                        label = { Text("Title") },
                        isError = showerror.value,
                        supportingText = {
                            if(title.value.isBlank()){
                                Text(text = "This field should not be blank",
                                    color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    //content field of the dialogue box
                    OutlinedTextField(
                        value = content.value,
                        onValueChange = { content.value = it
                            if (it.isNotBlank()) {
                                showerror.value = false
                            }},
                        label = { Text("Content") },
                        isError = showerror.value,
                        supportingText = {
                            if(content.value.isBlank()){
                                Text(text = "This field should not be blank",
                                    color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.Start)) {
                        Spacer(modifier = Modifier.width(26.dp))
                        //cancel button of the dialogue box
                        Button(
                            onClick = {
                                showDialog.value = false
                            },
                        ) {
                            Text(text = "Cancel")
                        }
                        Spacer(modifier = Modifier.width(45.dp))
                        //confirm button of the dialogue box
                        Button(onClick = {
                            if(title.value.isBlank() or content.value.isBlank()){
                                showerror.value = true
                                return@Button

                            }
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
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "NotesApp",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 36.sp
                                ))
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
                    Text(text = "No Items Are available to show please click on + to add note",
                        modifier = Modifier.padding(10.dp))
                }

            }
        state.value.data.isNotEmpty()->
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "NotesApp",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 36.sp
                                ))
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {showDialog.value = true }
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
                            EachNoteUI(notes = it)
                        }

                    }
                }

            }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EachNoteUI(
     notes: Notes,
     viewModel: MyViewMOdel = hiltViewModel()
) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = notes.title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = notes.content,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ))
            }
            IconButton(onClick = {
                viewModel.DeleteNotes(notes)
            },
                modifier = Modifier
                    .align(Alignment.End)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
                    .padding(4.dp)
                ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = Color.Red
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNote(notesid: Int,
               viewModel: MyViewMOdel = hiltViewModel()) {
    val state = viewModel.noteState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet = remember { mutableStateOf(false) }

    var title = remember { mutableStateOf("") }
    var content = remember { mutableStateOf("") }
    var notes = Notes(
        title = title.value,
        content = content.value
    )

    if(notesid != null){
        notes = state.value.data.find {
            it.id == notesid
        }!!
        title.value = notes.title
        content.value = notes.content

    }
    if (showSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showSheet.value = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Edit Notes",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = content.value,
                    onValueChange = { content.value = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(bottom = 24.dp),

                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick ={
                        showSheet.value = false
                    } ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            viewModel.UpsertNotes(notes = notes)
                            showSheet.value = false
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            }

        }
    }


}

