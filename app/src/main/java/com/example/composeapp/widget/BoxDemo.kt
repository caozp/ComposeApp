package com.example.composeapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun BoxPreview() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Yellow), contentAlignment = Alignment.Center) {
        Text("Box作用域内的组件可以通过Modifier.align覆盖Box设置的对齐方式contentAlignment",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(start = 30.dp, end = 30.dp)        //外边距
                .offset(y=100.dp)                           //偏移
                .border(                                    //边框
                    width = 1.dp, shape = RectangleShape,
                    color = Color.Red
                ).padding(15.dp),                     //内边距
            style = TextStyle(fontSize = 25.sp))

        Box(modifier = Modifier.size(250.dp).background(Color.LightGray),contentAlignment = Alignment.Center) {

            Text("外部Box设置的内部对齐方式Alignment.Center", style = TextStyle(fontSize = 25.sp), modifier = Modifier.padding(15.dp).border(
                width = 1.dp, shape = RectangleShape,
                color = Color.Red
            ))
        }
    }
}