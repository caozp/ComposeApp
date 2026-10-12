package com.example.composeapp.animate

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
//组件尺寸大小改变时的动画
fun animateContentSizePreview() {
    var expand by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Button(onClick = {
            expand = !expand
        }) {
            Text(if (expand) "Shrink" else "Expand")
        }

        Box(modifier = Modifier
            .background(Color.LightGray)
            .animateContentSize()) {
            Text(text = "iTerm2 should require little explanation for users accustomed to terminal emulators. " +
                    "Even if you are an experienced user, take the time to read through the highlights section of this document. " +
                    "It will familiarize you with some features of iTerm2 that you may not have seen in other terminal emulators that can make a real difference in the way you work.",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp),
                maxLines = if (expand) Int.MAX_VALUE else 2)
        }
    }
}