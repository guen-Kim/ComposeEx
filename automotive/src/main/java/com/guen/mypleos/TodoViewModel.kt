package com.guen.mypleos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    // Todo 목록
    private var _todos = mutableListOf(
        TodoItem(1, "Compose 공부하기"),
        TodoItem(2, "물 마시기", mutableStateOf(true)),
        TodoItem(3, "산책하기")
    )

    val todos : List<TodoItem> get() = _todos

    //새 할 일 입력 상태
    var newTodoText = mutableStateOf("")
        private set

    private var nextId = 4

    fun onTextChange(newText: String) {
        newTodoText.value = newText
    }

    fun addTodo() {
        if(newTodoText.value.isNotBlank()) {
            _todos.add(
                TodoItem(
                    id = nextId++,
                    text = newTodoText.value
                )
            )
            newTodoText.value = ""
        }
    }

    fun toggleTodo(todo: TodoItem) {
        todo.isDone.value = !todo.isDone.value
    }
}