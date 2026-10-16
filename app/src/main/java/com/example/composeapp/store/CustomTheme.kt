package com.example.composeapp.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composeapp.ui.theme.ComposeAppTheme

@Preview
@Composable
fun switchTheme() {
    val viewModel = PreferencesViewModel(PreferencesRepository(LocalContext.current))
    val darkMode: Boolean by viewModel.darkMode.collectAsStateWithLifecycle()
    ComposeAppTheme(darkMode) {
        Box(
            Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(if (darkMode) "深色模式" else "浅色模式")
                Switch(darkMode, onCheckedChange = {
                    viewModel.toggleDarkMode(it)
                },colors=SwitchDefaults.colors(uncheckedBorderColor=Color.White, checkedBorderColor = Color.White))
            }
        }
    }
}