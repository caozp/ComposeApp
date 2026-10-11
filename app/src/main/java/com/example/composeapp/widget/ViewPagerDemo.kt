package com.example.composeapp.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Preview
@Composable
fun SimplePager() {
    val pagerState = rememberPagerState(pageCount = {5})
    Column{
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) {page->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF2196F3)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Page $page",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
        }

        // 指示器
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isSelected) 12.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color(0xFF2196F3) else Color.Gray
                    ))
            }
        }
    }
}


@Preview
@Composable
fun MultiPager() {
    val pageState = rememberPagerState(pageCount = {5})
    HorizontalPager(
        state = pageState,
        //contentPadding + pageSpacing 配合使用可实现一屏多页和卡片间距
        contentPadding = PaddingValues(horizontal = 30.dp),
        pageSpacing = 16.dp
    ) { page->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF6200EE)
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text("Card $page",color = Color.White, fontSize = 20.sp)
            }
        }
    }
}


//带缩放动画的轮播
@SuppressLint("FrequentlyChangingValue")
@Preview
@Composable
fun ZoomPager() {
    val pageCount = 5
    val pageState = rememberPagerState(pageCount = { pageCount })

    // 自动轮播  用snapshotFlow 替代while true死循环的方式
    LaunchedEffect(pageState) {
        snapshotFlow { pageState.settledPage } // 监听稳定后的页
            .collectLatest { page ->
                delay(5000)
                val nextPage = (pageState.currentPage + 1) % pageCount
                pageState.animateScrollToPage(nextPage)
            }
    }
    //另外可以监听pageState.isScrollInProgress
    /**
     *       snapshotFlow { pageState.isScrollInProgress }
     *             .distinctUntilChanged()
     *             .collectLatest { isScrolling ->
     *                 if (!isScrolling) {
     *                    delay(5000)
     *                    ...
     *                 }
     *             }
     */

    HorizontalPager(
        state = pageState,
        //contentPadding + pageSpacing 配合使用可实现一屏多页和卡片间距
        contentPadding = PaddingValues(horizontal = 48.dp),
        pageSpacing = 16.dp
    ) { page->
        val offset = pageState.currentPageOffsetFraction
        val scale = if (page == pageState.currentPage) {
            1f - 0.2f * kotlin.math.abs(offset)
        } else {
            0.8f
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFF5722)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Page $page",
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}


@Preview
@Composable
fun ControlledPager() {
    val pagerState = rememberPagerState(pageCount = {6})
    val scope = rememberCoroutineScope()

    Column() {
        HorizontalPager(state = pagerState) {page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF4CAF50)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Page $page",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        (pagerState.currentPage -1).coerceAtLeast(0)
                    )
                }
            }, enabled = pagerState.currentPage != 0) {  Text("上一页")}

            Button(onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        (pagerState.currentPage +1).coerceAtMost(pagerState.pageCount -1)
                    )
                }
            }, enabled = pagerState.currentPage != pagerState.pageCount -1) {  Text("下一页")}
        }
    }
}