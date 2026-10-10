package com.example.composeapp.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun FlowRowPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(10.dp)) {
        Text("Flow List选择:")
        FlowListSample()
        Text("Flow Map选择:")
        FlowRowMapSample()
        Text("Flow 单选:")
        SingleFlowListSample()
    }
}


@Composable
fun FlowListSample() {
    val filters = listOf(
        "Kotlin",
        "Java",
        "Compose",
        "Flutter",
        "Vue",
        "Android"
        )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { title ->
            var selected by remember { mutableStateOf(false) }
            val leadingIcon: @Composable () -> Unit = {
                Icon(Icons.Default.Check,null)
            }

            FilterChip(selected,
                onClick = {
                    selected = !selected
                },
                label = {
                    Text(title)
                },
                leadingIcon = if(selected) leadingIcon else null)
        }
    }
}


@Composable
fun FlowRowMapSample() {
    var selectedMaps = remember {
        mutableStateMapOf<String,Boolean>(
            "Kotlin" to false,
            "Java" to false,
            "Compose" to false,
            "Flutter" to false,
            "Vue" to false,
            "Android" to false
        )
    }
    val leadingIcon: @Composable () -> Unit = {
        Icon(Icons.Default.Check,null)
    }

    FlowRow(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        selectedMaps.forEach { (string, bool) ->
            key(string) {
                FilterChip(
                    selected = bool,
                    onClick = {
                        selectedMaps[string] = !bool
                    },
                    label = {
                        Text(string)
                    },
                    leadingIcon = if(bool) leadingIcon else null
                )
            }
        }
    }
}


@Composable
fun SingleFlowListSample() {
    var selected by remember { mutableStateOf(0) }
    val filters = listOf(
        "Kotlin",
        "Java",
        "Compose",
        "Flutter",
        "Vue",
        "Android"
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEachIndexed { index,title ->

            val leadingIcon: @Composable () -> Unit = {
                Icon(Icons.Default.Check,null)
            }

            FilterChip(index==selected,
                onClick = {
                    selected = index
                },
                label = {
                    Text(title)
                },
                leadingIcon = if(index == selected) leadingIcon else null)
        }
    }
}