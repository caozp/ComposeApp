package com.example.composeapp.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.R

@Preview
@Composable
fun ImagePreview() {
    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {
        Text("原图,尺寸100.dp*100.dp", Modifier.align(Alignment.CenterHorizontally))
        Image(painterResource(R.drawable.girl), contentDescription = null,
            modifier = Modifier
                .size(100.dp, 100.dp)
                .clip(RectangleShape)
                .align(Alignment.CenterHorizontally))

        ImageItemContentScale(ContentScale.Fit,"Fit"
            ,"默认值.保持宽高比,完整显示图片。可能留有留白，图片不会裁剪")

        ImageItemContentScale(ContentScale.Crop,"Crop"
            ,"保持宽高比，缩放图片使其填满容器。可能裁剪图片，但不会留白")

        ImageItemContentScale(ContentScale.FillBounds,"FillBounds"
            ,"不保持宽高比，拉伸图片以精确填充容器。图片变形")

        ImageItemContentScale(ContentScale.FillWidth,"FillWidth"
            ,"保持宽高比，缩放图片使宽度与容器宽度一致。高度可能超出或小于容器，超出部分可能被裁减或留白")

        ImageItemContentScale(ContentScale.FillHeight,"FillHeight"
            ,"保持宽高比，缩放图片使高度与容器高度一致。")

        ImageItemContentScale(ContentScale.Inside,"Inside"
            ,"保持宽高比，仅当图片大于容器时才缩放。图片不会放大只会缩小，可能留白。")

        ImageItemContentScale(ContentScale.None,"None"
            ,"不缩放，按原始尺寸显示图片。图片可能超出容器或被裁减")
    }
}

@Composable
fun ImageItemContentScale(type:ContentScale,typeString: String,detail: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        Row() {
            Image(painter = painterResource(R.drawable.girl),
                null,
                contentScale = type,
                modifier = Modifier
                    .size(60.dp, 100.dp)
                    .background(Color.DarkGray))

            Column(modifier = Modifier.padding(10.dp)) {
                Text(typeString,
                    style = MaterialTheme.typography.titleLarge,
                    )

                Text(detail,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}