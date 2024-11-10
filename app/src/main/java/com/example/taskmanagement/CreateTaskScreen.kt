package com.example.taskmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(taskViewModel: TaskViewModel, navController: NavController, id: Int?) {
    val existingTask = if (id != null) taskViewModel.getById(id) else null
    var newTaskTitle by remember(id) { mutableStateOf(existingTask?.title ?: "") }
    var newTaskDescription by remember(id) { mutableStateOf(existingTask?.description ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (id == null) "Create Task" else "Edit Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BasicTextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textStyle = MaterialTheme.typography.titleMedium,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (newTaskTitle.isEmpty())
                            Text(
                                "Enter title",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        innerTextField()
                    }
                }
            )
            BasicTextField(
                value = newTaskDescription,
                onValueChange = { newTaskDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (newTaskDescription.isEmpty()) Text("Add details")
                        innerTextField()
                    }
                }
            )
            Button(
                onClick = {
                    if (newTaskTitle.isNotEmpty() && newTaskDescription.isNotEmpty()) {
                        if (id == null) {
                            taskViewModel.add(newTaskTitle, newTaskDescription)
                        } else {
                            taskViewModel.edit(id, newTaskTitle, newTaskDescription)
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                Text(if (id == null) "Add Task" else "Update Task")
            }
        }
    }
}