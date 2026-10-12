package com.example.composeapp.animate

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun animateContentPreview() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column{
            var count by remember { mutableIntStateOf(0) }
            Button(onClick = {
                count++
            }) { Text("+1")}

            Button(onClick = {
                count--
            }) { Text("-1")}
            //用来实现不同组件间切换的动画
            //参数接受一个targetState和一个Content
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if(targetState > initialState) {
                        slideInVertically { it } + fadeIn() togetherWith
                                slideOutVertically { -it } + fadeOut()
                    } else {
                        slideInVertically { -it } + fadeIn() togetherWith
                                slideOutVertically { it } + fadeOut()
                    }
                }) { targetCount ->
                Text("$targetCount", fontSize = 80.sp)
            }

            Text("$count", fontSize = 80.sp)
        }
    }
}