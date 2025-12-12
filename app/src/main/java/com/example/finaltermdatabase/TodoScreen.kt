package com.example.finaltermdatabase

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.finaltermdatabase.ui.theme.Color1
import com.example.finaltermdatabase.ui.theme.Color2
import com.example.finaltermdatabase.ui.theme.Color3
import com.example.finaltermdatabase.ui.theme.Color4
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun TodoScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val todoDAO by produceState<TodoDAO?>(initialValue = null) {
        value = TodoDatabase.getDatabase(context).todoDao()
    }
    val todos = todoDAO?.getAllTodos()?.collectAsState(initial = emptyList())?.value ?: emptyList()
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .background(Color1)
            .fillMaxSize(),
        topBar = {
            Topbar()
        },
        floatingActionButton = {
            FabButton {
                showDialog = true
            }
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        if (showDialog) {
            TodoInputDialog(onDismissRequest = { showDialog = false }) { todo ->
                scope.launch {
                    todoDAO?.upsertTodo(todo)
                    showDialog = false
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = todos, key = { it.id }) { todo ->
                TodoItem(todo = todo) {
                    scope.launch {
                        todoDAO?.deleteTodo(todo)
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onDelete: () -> Unit) {
    ListItem(
        headlineContent = {
            Text(
                text = todo.title,
                color = Color4
            )
        },
        supportingContent = {
            Text(
                text = todo.text,
                color = Color4
            )
        },
        trailingContent = {
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "delete todo",
                    tint = Color4
                )
            }
        },
        overlineContent = {
            todo.createdAt?.let {date ->
                val formatter= SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
                Text(
                    text = formatter.format(date),
                    color = Color3
                )
            }
        },
        modifier = Modifier
            .background(
                color = Color2,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth(),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Todo List",
                color = Color4
            )
        },
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 0.dp)
            .statusBarsPadding()
            .fillMaxWidth()
            .background(
                color = Color2, shape = RoundedCornerShape(24.dp)
            ),
        navigationIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_menu_24),
                    contentDescription = "menu icon",
                    tint = Color4
                )
            }
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_more_vert_24),
                    contentDescription = "more vertical icon",
                    tint = Color4
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun FabButton(onFabClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = {
            onFabClick()
        },
        text = {
            Text(
                text = "Add Todo",
            )
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add todo",
            )
        },
        containerColor = Color3,
        contentColor = Color1
    )
}

@Composable
fun TodoInputDialog(onDismissRequest: () -> Unit, onSaveClick: (todo: Todo) -> Unit) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        val title = rememberTextFieldState()
        val text = rememberTextFieldState()
        val saveButtonEnabled by remember(title.text, text.text) {
            derivedStateOf {
                title.text.isNotBlank() && text.text.isNotBlank()
            }
        }
        Column(
            modifier = Modifier
                .background(color = Color2, shape = RoundedCornerShape(24.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Fill Data to Add the Todo",
                color = Color4,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                state = title,
                placeholder = {
                    Text(
                        text = "Enter title",
                    )
                },
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color4,
                    unfocusedTextColor = Color4,
                    unfocusedPlaceholderColor = Color1,
                    focusedPlaceholderColor = Color2,
                    unfocusedBorderColor = Color3,
                    focusedBorderColor = Color4,
                    cursorColor = Color4
                )
            )
            OutlinedTextField(
                state = text,
                placeholder = {
                    Text(
                        text = "Enter Text",
                    )
                },
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth()
                    .aspectRatio(3 / 2f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color4,
                    unfocusedTextColor = Color4,
                    unfocusedPlaceholderColor = Color1,
                    focusedPlaceholderColor = Color2,
                    unfocusedBorderColor = Color3,
                    focusedBorderColor = Color4,
                    cursorColor = Color4
                ),

                )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        onDismissRequest()
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color4,
                        containerColor = Color.Transparent,
                    ),
                    border = BorderStroke(width = 2.dp, color = Color1)
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
                FilledTonalButton(
                    onClick = {
                        val todo = Todo(
                            title = title.text.toString(),
                            text = text.text.toString(),
                            createdAt = Date(
                                System.currentTimeMillis()
                            ),
                            isFavorite = false
                        )
                        onSaveClick(todo)
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color1,
                        contentColor = Color4,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color3
                    ),
                    enabled = saveButtonEnabled
                ) {
                    Text(
                        text = "Save"
                    )
                }
            }
        }
    }
}