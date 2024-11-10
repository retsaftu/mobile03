package com.example.taskmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(taskViewModel: TaskViewModel, navController: NavController) {
    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Task List") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createTask") }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks.size) { index ->
                TaskItem(
                    task = tasks[index],
                    onCompleteTask = { taskViewModel.toggle(tasks[index].id) },
                    onEditClick = { navController.navigate("editTask/${tasks[index].id}") },
                    onDeleteClick = { taskViewModel.delete(tasks[index].id) }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCompleteTask: (Task) -> Unit,
    onEditClick: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onCompleteTask(task) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isComplete,
                onCheckedChange = { onCompleteTask(task) }
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                )
                if (task.description.isNotEmpty())
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                    )
            }
            IconButton(
                onClick = { onEditClick(task) }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Task"
                )
            }
            IconButton(
                onClick = { onDeleteClick(task) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }
    }
}