package com.example.composeapp.flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Preview
@Composable
fun requestLogin() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSubmitEnabled by remember { mutableStateOf(false) }

    //snapshotFlow把两个state变成flow
    LaunchedEffect(Unit) {
        snapshotFlow { username to password } //to是kotlin扩展函数，用来创建一个Pair
        //约等于 Pair("username","password"),如果是三个state,要改成Triple或自定义data class
            .map { (u,p)-> u.length >=3 && p.length>=6 }
            .distinctUntilChanged()
            .collect { enable->
                isSubmitEnabled = enable
            }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            username,
            onValueChange = {
                username = it
            },
            label = { Text("用户名至少为3位") }
        )

        OutlinedTextField(
            password,
            onValueChange = {
                password = it
            },
            label = { Text("密码至少为6位")}
        )

        Button(enabled = isSubmitEnabled, onClick = {}) {
            Text("提交")
        }
    }
}


@Preview
@Composable
fun queryResult() {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(500)
            .distinctUntilChanged()
            .collectLatest{q->
                results = if(q.isBlank()) {
                    emptyList()
                } else {
                    searchApi(q)
                }
            }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(query,
            onValueChange = {
                query = it
            },
            label = {Text("搜索")},
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn{
            items(results) {item->
                Text(item, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

suspend fun searchApi(query: String):List<String> {
    delay(500)
    return List(5){ "$query 的结果是${it+1}"}
}