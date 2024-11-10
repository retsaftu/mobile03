package com.example.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "taskList") {
                composable("taskList") {
                    TasksScreen(taskViewModel = taskViewModel, navController = navController)
                }
                composable("editTask/{id}") { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getString("id")!!.toInt()
                    CreateTaskScreen(taskViewModel = taskViewModel, navController = navController, id = taskId)
                }
                composable("createTask") {
                    CreateTaskScreen(taskViewModel = taskViewModel, navController = navController, id = null)
                }
            }
        }
    }
}