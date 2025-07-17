package com.guen.mypleos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // xml, inflate 코드
            TodoListScreenUpdate()
        }
    }
}

@Composable
fun CounterApp() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Count: $count", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { count++ }) {
            Text("Click me!")
        }
    }
}


@Composable
fun BoxExample() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(text = "가운데 정렬된 텍스트", fontSize = 20.sp)
    }
}


@Composable
fun ToggleTextExample() {
    var isHello by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isHello) "a" else "b",  // ← 인자 명시
            fontSize = 24.sp                   // ← 정확한 이름
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { isHello = !isHello }) {
            Text(text = "바꾸기")
        }
    }
}

@Composable
fun SimpleCalculator() {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "덧셈 계산기", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = input1,
            onValueChange = { input1 = it },
            label = { Text("숫자 1") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = input2,
            onValueChange = { input2 = it },
            label = { Text(text = ("숫자 2")) })


        Button(
            onClick = {
                val num1 = input1.toIntOrNull()
                val num2 = input2.toIntOrNull()
                if (num1 != null && num2 != null) {
                    result = num1 + num2
                } else {
                    result = null
                }

            }
        ) {
            Text(text = "+")
        }


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = result?.let { "결과: $it" } ?: "숫자를 정확히 입력해주세요",
            fontSize = 20.sp
        )


    }
}

//data class TodoItem(val id: Int, val text: String, var isDone: Boolean)
//상태변화 감지를 위해 리컴포지션 발생 데이터 코드로 수정
data class TodoItem(val id: Int, val text: String, val isDone: MutableState<Boolean> = mutableStateOf(false))

@Composable
fun TodoListScreen() {

    var todos = remember {
        mutableStateListOf(
            TodoItem(1, "뇌전캐기", mutableStateOf(false)),
            TodoItem(2, "일비캐기", mutableStateOf(false)),
            TodoItem(3, "토비캐기", mutableStateOf(false))
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Todo List", fontSize = 26.sp)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todos) { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            todo.isDone.value = !todo.isDone.value
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = todo.isDone.value,
                        onCheckedChange = null
                    )
                    Text(
                        text = todo.text,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun TodoListScreenUpdate() {
    val todos = remember {
        mutableStateListOf(
            TodoItem(1, "Compose 공부하기"),
            TodoItem(2, "Compose 공부하기", mutableStateOf(true)),
            TodoItem(3, "xml 공부하기"),
        )
    }

    var newTodoText by remember { mutableStateOf(" ") }
    var nextId by remember { mutableStateOf(4) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row( //행
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = newTodoText,
                onValueChange = { newTodoText = it },
                modifier = Modifier.weight(1f),
                label = { Text("할 일 입력") }
            )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if(newTodoText.isNotBlank()) {
                    todos.add(
                        TodoItem(id = nextId++, text = newTodoText)
                    )
                    newTodoText = ""
                }
            }
        ) {
            Text("추가")
        }



        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text("Todo List", fontSize = 24.sp)

    LazyColumn {
        items(todos, key = { it.id }) {
            todo ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { todo.isDone.value = !todo.isDone.value }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = todo.isDone.value, onCheckedChange = null)
                Text(text = todo.text, fontSize = 18.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
