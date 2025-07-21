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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // xml, inflate 코드
            TodoApp()
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
data class TodoItem(
    val id: Int,
    val text: String,
    val isDone: Boolean = false
)

//@Composable
//fun TodoListScreen() {
//
//    var todos = remember {
//        mutableStateListOf(
//            TodoItem(1, "뇌전캐기", mutableStateOf(false)),
//            TodoItem(2, "일비캐기", mutableStateOf(false)),
//            TodoItem(3, "토비캐기", mutableStateOf(false))
//        )
//    }
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(text = "Todo List", fontSize = 26.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn {
//            items(todos) { todo ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clickable {
//                            todo.isDone.value = !todo.isDone.value
//                        }
//                        .padding(vertical = 8.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Checkbox(
//                        checked = todo.isDone.value,
//                        onCheckedChange = null
//                    )
//                    Text(
//                        text = todo.text,
//                        fontSize = 18.sp,
//                        modifier = Modifier.padding(start = 8.dp)
//                    )
//                }
//            }
//        }
//    }
//}


@Composable
fun TodoListScreenUpdate() {
    val todos = remember {
        mutableStateListOf(
            TodoItem(1, "Compose 공부하기"),
            TodoItem(2, "Compose 공부하기"),
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
        ) {
            TextField(
                value = newTodoText,
                onValueChange = { newTodoText = it },
                modifier = Modifier.weight(1f),
                label = { Text("할 일 입력") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTodoText.isNotBlank()) {
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

//    LazyColumn {
//        items(todos, key = { it.id }) { todo ->
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clickable { todo.isDone.value = !todo.isDone.value }
//                    .padding(vertical = 8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Checkbox(checked = todo.isDone.value, onCheckedChange = null)
//                Text(text = todo.text, fontSize = 18.sp, modifier = Modifier.padding(start = 8.dp))
//            }
//        }
//    }
}


//3. TodoScreen Composable – 상태는 ViewModel에서 가져오고, 입력값은 rememberSaveable로 관리
//@Composable
//fun TodoListScreenUpdateViewModel(viewModel: TodoViewModel = viewModel()) {
//    val todos = viewModel.todos
//    var input by rememberSaveable { mutableStateOf("") } // TextField 입력 필드 상태 유지
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TextField(
//                value = input,
//                onValueChange = { input = it},
//                modifier = Modifier.weight(1f),
//                label = {Text("할 일 입력")}
//                )
//
//            Spacer(modifier = Modifier.width(8.dp))
//            Button(onClick = {viewModel.addTodo(input)}) {
//                Text(text = "추가")
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("📝 Todo List", fontSize = 24.sp)
//
//        LazyColumn {
//            items(todos, key = {it.id}) { todo ->
//                TodoRow(todo = todo,
//                    onToggle = {viewModel.toggleTodo(todo.id)})
//            }
//        }
//    }
//}

//4. TodoRow – State를 UI로 끌어오지 않고, 외부(onToggle)로 위임

/*
* 장점:TodoRow를 입력 받아 사용하기 때문에 TodoListScreenUpdateViewModel의 LazyColumn를 재사용할 수 있다.
* 단점:확장할 필요가 없다면 코드가 길어진다.
* 확장
* - onToggle: () -> Unit 의 비지니스 로직을 확장한다.
* */
@Composable
fun TodoRow(todo: TodoItem, onToggle: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { onToggle() }
        )
        Text(
            text = todo.text,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp
        )
    }
}

@Composable
fun TodoApp(viewModel: TodoViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    navController.navigate("detail/$todoId")
                }
            )
        }
        composable("detail/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull()
            val todo = todoId?.let { id -> viewModel.todos.find { it.id == id } }

            if (todo != null) {
                TodoDetailScreen(
                    todo = todo,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Text("Todo를 찾을 수 없습니다.")
            }
        }
    }
}

@Composable
fun TodoListScreen(
    viewModel: TodoViewModel,
    onTodoClick: (Int) -> Unit
) {
    val todos = viewModel.todos

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(todos, key = { it.id }) { todo ->
            TodoRow(
                todo = todo,
                onToggle = { viewModel.toggleDone(todo.id) },
                onClick = { onTodoClick(todo.id) }
            )
        }
    }
}

@Composable
fun TodoDetailScreen(
    todo: TodoItem,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("할 일 상세", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("ID: ${todo.id}")
        Text("내용: ${todo.text}")
        Text("완료 여부: ${if (todo.isDone) "완료" else "미완료"}")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text(text = "뒤로가기")
        }
    }
}