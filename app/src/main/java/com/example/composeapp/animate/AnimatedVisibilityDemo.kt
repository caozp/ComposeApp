package com.example.composeapp.animate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.R

@Preview
@Composable
fun VisibilityPreview() {
    var visible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            visible = !visible
        }) {
            if(visible) {
                Text("隐藏图片")
            } else {
                Text("展示图片")
            }
        }

        // animateEnterExit 还可以为AnimatedVisibility内部子元素单独设置动画
        AnimatedVisibility(
            visible,
            enter = slideInHorizontally(),
            //滑出方向由左向右
            exit = slideOutHorizontally(targetOffsetX = {it})
        ) {
            Image(painterResource(R.drawable.girl),null,
                modifier = Modifier.size(60.dp).clip(CircleShape)
                    .animateEnterExit(
                        enter = scaleIn(),
                        exit = scaleOut()
                    )
            )
        }


        AnimatedVisibility(
            visible,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally(targetOffsetX = {it})
        ) {
            Image(painterResource(R.drawable.girl),null,
                modifier = Modifier.size(60.dp).clip(CircleShape)
            )
        }
    }
}