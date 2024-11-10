package com.example.taskmanagement

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel: ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(listOf())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun add(title: String, description: String) {
        val newTask = Task(
            title = title,
            description = description
        )

        _tasks.update { it + newTask }
    }

    fun getById(id: Int): Task? {
        return _tasks.value.find { it.id == id }
    }

    fun delete(id: Int) {
        _tasks.update { it.filterNot { currTask -> currTask.id == id } }

    }

    fun edit(id: Int, newTitle: String, newDescription: String) {
        _tasks.update { tasks ->
            tasks.map { task ->
                if (task.id != id) task
                else task.copy(title = newTitle, description = newDescription)
            }
        }
    }

    fun toggle(id: Int) {
        _tasks.update { tasks ->
            tasks.map { task ->
                if (task.id != id) task
                else task.copy(isComplete = !task.isComplete)
            }
        }
    }
}