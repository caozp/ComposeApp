package com.example.composeapp.widget


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ShowCheckBox() {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround) {
        CheckBoxPreview()
        SwitchPreview()
        TriStateCheckBoxPreview()
    }
}


@Composable
fun CheckBoxPreview() {
    val checked = remember { mutableStateOf(false) }
    Checkbox(checked.value, onCheckedChange = {
        checked.value = it
    },colors= CheckboxDefaults.colors(checkedColor = Color.Red))
}


@Composable
fun SwitchPreview() {
    var checked by remember { mutableStateOf(false) }
    Switch(checked, onCheckedChange = {
        checked = it
    })
}


@Composable
fun TriStateCheckBoxPreview() {
    //这是一个解构函数。解构需要componentN函数
    //在Compose中，针对mutableState的解构行为
    //component1和component2通常是这样的伪代码
    //component1 返回this.value
    //component2 返回一个匿名函数，这个函数接受一个新值，并将其赋值给this.value
    val (state,onStateChange) = remember { mutableStateOf(true) }
    val (state2,onStateChange2) = remember { mutableStateOf(true) }
    val parentState = remember(state,state2) {
        if(state && state2) ToggleableState.On
        else if(!state && !state2) ToggleableState.Off
        else ToggleableState.Indeterminate
    }
    val onParentClick = {
        val s = parentState!= ToggleableState.On
        onStateChange(s)
        onStateChange2(s)
    }

    Column() {
        TriStateCheckbox(
            state=parentState,
            onClick = onParentClick
        )
        Checkbox(state,onStateChange, Modifier.offset(12.dp))
        Checkbox(state2,onStateChange2, Modifier.offset(12.dp))
    }
}