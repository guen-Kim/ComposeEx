package com.guen.mypleos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private var nextId = 0

    private val _todos = mutableListOf<TodoItem>()
    val todos : List<TodoItem> get() = _todos

    fun addTodo(text: String) {
        if(text.isNotBlank()) {
            _todos.add(
                TodoItem(
                    id = nextId++, text = text
                )
            )
        }
    }

    fun toggleTodo(id: Int) {
        val index = todos.indexOfFirst { it.id == id }
        if(index != -1) {
            val item = _todos[index]
            _todos[index] = item.copy(isDone = !item.isDone)
        }
    }
}