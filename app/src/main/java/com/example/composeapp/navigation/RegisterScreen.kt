package com.example.composeapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun RegisterScreen(
    onRegisterSuccess:()->Unit,
    onBack:()->Unit,
    viewModel: UserViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSubmitEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        snapshotFlow { username to password }
            .map { (u,p)-> u.length >=3 && p.length>=6 }
            .distinctUntilChanged()
            .collect { enable->
                isSubmitEnabled = enable
            }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("注册页", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                username,
                onValueChange = {
                    username = it
                },
                label = { Text("用户名至少为3位") }
            )

            OutlinedTextField(
                password,
                onValueChange = {
                    password = it
                },
                label = { Text("密码至少为6位") }
            )

            Button(enabled = isSubmitEnabled, onClick = {
                viewModel.login(username)
                onRegisterSuccess()
            }) {
                Text("注册")
            }

            Button(onClick = {
                onBack()
            }) {
                Text("返回")
            }
        }
    }
}