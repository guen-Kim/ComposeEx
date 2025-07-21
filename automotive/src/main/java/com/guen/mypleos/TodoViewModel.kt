package com.guen.mypleos

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private var nextId = 0

    private var _todos = mutableStateListOf(
        TodoItem(1, "공부하기", false),
        TodoItem(2, "산책하기", true)
    )
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

    fun toggleDone(id: Int) {
        val index = _todos.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = _todos[index]
            _todos[index] = item.copy(isDone = !item.isDone)
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