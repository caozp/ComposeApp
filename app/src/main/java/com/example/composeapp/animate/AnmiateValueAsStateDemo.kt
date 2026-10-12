package com.example.composeapp.animate

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview
@Composable
fun animateValueAsStatePreview() {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 1.4f else 1f,
        animationSpec = spring(
            Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow),
        label = "scale"
    )

    if (scale == 1.4f) {
        pressed = false
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.size(120.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                ).background(Color(0xFF4CAF50), CircleShape)
                .clickable{
                    pressed = !pressed
                }, contentAlignment = Alignment.Center
        ) {
            Text("click", fontSize = 20.sp)
        }
    }
}

// 实现放大后再收缩回原大小
// animatable 数值包装器， 它的animateTo方法根据数值的变化设置动画效果
// 动画规格
// 1. spring
// 2. tween
// 3  keyframes
// 4. repeatable
@Preview
@Composable
fun animatablePreview() {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.size(120.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                ).background(Color(0xFF4CAF50), CircleShape)
                .clickable{
                    scope.launch {
                        scale.snapTo(1f)
                        scale.animateTo(targetValue = 1.5f, tween(150))
                        scale.animateTo(targetValue = 1f, spring(Spring.DampingRatioMediumBouncy))
                    }
                }, contentAlignment = Alignment.Center
        ) {
            Text("click", fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun keyframesPreview() {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    val spec = keyframes {
        durationMillis = 1000
        1f at 0 with LinearOutSlowInEasing
        1.5f at 500 with FastOutLinearInEasing
        1f at 1000
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.size(120.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                ).background(Color(0xFF4CAF50), CircleShape)
                .clickable{
                    scope.launch {
                        scale.snapTo(1f)
                        scale.animateTo(targetValue = 1f, spec)
                    }
                }, contentAlignment = Alignment.Center
        ) {
            Text("click", fontSize = 20.sp)
        }
    }
}


@Preview
@Composable
fun repeatPreview() {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    val repeat = repeatable( //无限循环用infiniteRepeatable
        4,//循环次数
        tween<Float>(1000),
        RepeatMode.Reverse
    )

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.size(120.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                ).background(Color(0xFF4CAF50), CircleShape)
                .clickable{
                    scope.launch {
                        scale.snapTo(1f)
                        scale.animateTo(targetValue = 1.5f, repeat)
                    }
                }, contentAlignment = Alignment.Center
        ) {
            Text("click", fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun TextfieldFocus() {
    var text by remember { mutableStateOf("") }
    var focusState by remember { mutableStateOf(false) }
    val sizeVariable = remember { Animatable(0f) }

    LaunchedEffect(focusState) {
        sizeVariable.animateTo(if(focusState) 0.8f else 0.5f)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BasicTextField(value=text,
            onValueChange = {
                text = it
            }, maxLines = 1,
            decorationBox = {innerTextField->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(40.dp).fillMaxWidth(sizeVariable.value).clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                ) {

                    Icon(Icons.Default.Search,null, modifier = Modifier.padding(start = 10.dp))

                    Box(Modifier
                        .weight(1f)
                        .padding(start = 12.dp, end = 12.dp)) {
                        innerTextField()
                    }

                    IconButton(onClick = {
                        text = ""
                    }) {
                        Icon(Icons.Default.Close,null)
                    }
                }
            }, modifier = Modifier.onFocusChanged{
                focusState = it.isFocused
            })
    }
}

