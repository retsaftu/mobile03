package com.example.taskmanagement

data class Task(
    val id: Int = generateId(),
    var title: String,
    var description: String,
    var isComplete: Boolean = false,
) {
    companion object {
        private var id = 0

        private fun generateId(): Int {
            return ++id
        }
    }
}