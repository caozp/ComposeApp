package com.example.composeapp.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.R

@Preview
@Composable
fun IntrinsicSizePreView(){
    Column() {
        //分割线撑满Row高度
        Row(
            Modifier
                .padding(10.dp).border(2.dp, Color.Gray, shape = RectangleShape)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Image(painterResource(R.drawable.girl), null, modifier = Modifier.padding(end = 10.dp))

            Divider(color = Color.Black, modifier = Modifier.width(1.dp).fillMaxHeight())


            Text("abc", Modifier.weight(1f).wrapContentWidth(Alignment.End))
        }


        //分割线与Column一样宽
        Column(Modifier
            .padding(10.dp).border(2.dp, Color.Gray, shape = RectangleShape)
            .width(IntrinsicSize.Max)
            ,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(painterResource(R.drawable.girl), null)

            Divider(color = Color.Black, modifier = Modifier.height(1.dp))


            Text("android.intent.action.MAIN cat=and")
        }
    }
}