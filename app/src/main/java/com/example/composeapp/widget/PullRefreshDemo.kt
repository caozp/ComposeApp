package com.example.composeapp.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//官方推荐PullToRefreshBox
@Preview
@Composable
fun SwipeToRefreshTest() {
    val list = remember {
        List(4) { "Item $it"}.toMutableStateList()
    }
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(false) }
    PullToRefreshBox(
        isRefreshing =  state,
        onRefresh = {
            scope.launch {
                state = true
                delay(2000)
                list.add("Item ${list.size}")
                state = false
            }
        },
        indicator = {
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
            ) {
                if (state) {
                    // 自定义加载动画
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) {item->
                ListItem({
                    Text(text=item)
                })
            }
        }
    }
}