package com.example.composeapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ColumnPreview() {
    Column() {
        /**
         * SpaceAround:把孩子们放置，让他们沿主轴均匀分布，
         * 第一位孩子前面和最后一位孩子后面留空，但只有任意两个连续孩子之间空间的一半。
         */
        RowItem("SpaceAround",Arrangement.SpaceAround)
        RowItem("SpaceBetween",Arrangement.SpaceBetween)
        RowItem("SpaceEvenly",Arrangement.SpaceEvenly)
        RowItem("Center",Arrangement.Center)
        RowItem("Start",Arrangement.Start)
        RowItem("End",Arrangement.End)
    }
}


@Composable
fun RowItem(title: String,arrange: Arrangement.Horizontal) {
    Column  {
        Text(title, modifier = Modifier.align(Alignment.CenterHorizontally).padding(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = arrange
        ) {
            Text(
                "A",
                modifier = Modifier.border(width = 2.dp, shape = RectangleShape, color = Color.Red)
                    .background(Color.Red).padding(20.dp)
            )
            Text(
                "B",
                modifier = Modifier.border(
                    width = 2.dp,
                    shape = RectangleShape,
                    color = Color.Green
                ).background(Color.Green).padding(20.dp)
            )
            Text(
                "C",
                modifier = Modifier.border(
                    width = 2.dp,
                    shape = RectangleShape,
                    color = Color.LightGray
                ).background(Color.LightGray).padding(20.dp)
            )
        }
        Spacer(Modifier.fillMaxWidth().height(1.dp).background(Color.Black))
    }
}