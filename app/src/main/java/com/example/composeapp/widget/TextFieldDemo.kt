package com.example.composeapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TextFieldPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(10.dp, end = 10.dp)) {
        Text("OutlineTextField",style = MaterialTheme.typography.titleLarge)
        OutlineTextFieldSample()
        Text("BasicTextField", style = MaterialTheme.typography.titleLarge)
        BasicTextFieldSample()
    }
}


@Composable
fun OutlineTextFieldSample() {
    var text by remember { mutableStateOf("") }
    var hidepwdState by remember { mutableStateOf(true) }
    OutlinedTextField(value = text, onValueChange = {
        text = it
    }, label = {
        Text("用户密码")
    },leadingIcon = {
        Icon(Icons.Default.Password,null)
    }, visualTransformation = if(hidepwdState){
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }, trailingIcon = {
        IconButton(onClick = {
            hidepwdState = !hidepwdState
        }) {
            Icon(if(hidepwdState) {
                Icons.Default.VisibilityOff
            } else {
                Icons.Default.Visibility
            },null)
        }
    })
}

@Composable
fun BasicTextFieldSample() {
    var text by remember { mutableStateOf("") }
    BasicTextField(value=text,
        onValueChange = {
            text = it
        }, maxLines = 1,
        decorationBox = {innerTextField->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(60.dp).width(300.dp).clip(RoundedCornerShape(4.dp))
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
        },)
}