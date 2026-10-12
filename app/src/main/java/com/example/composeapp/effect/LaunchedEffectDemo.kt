package com.example.composeapp.effect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Preview
@Composable
fun loadData() {
    var data by remember { mutableStateOf("加载中") }
    //只能在Composable组合函数中使用
    //自动启动
    //自动取消  离开页面时，协程自动取消，不会泄漏
    //key的作用  key发生变化 会取消旧协程 启动新协程
    LaunchedEffect(Unit) {
        delay(2000)
        data = "加载完成，这是页面数据"
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = data, fontSize = 18.sp)
    }
}

@Preview()
@Composable
fun loadDataByClick() {
    var data by remember { mutableStateOf("加载中") }
    //只能在Composable组合函数中使用
    //自动启动
    //自动取消  离开页面时，协程自动取消，不会泄漏
    //key的作用  key发生变化 会取消旧协程 启动新协程
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Button(onClick = {
            scope.launch {
                delay(2000)
                data = "加载完成"
            }
        }) {
            Text(text = data, fontSize = 18.sp)
        }
    }
}


