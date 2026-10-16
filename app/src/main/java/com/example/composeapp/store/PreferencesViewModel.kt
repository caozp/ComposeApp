package com.example.composeapp.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferencesViewModel(private val repository: PreferencesRepository): ViewModel() {
    val darkMode = repository.darkModeFlow
        .stateIn(//转换成stateFlow
            viewModelScope,
            //WhileSubscribed 没有订阅就会被取消
            SharingStarted.WhileSubscribed(5000),false
        )

    fun toggleDarkMode(enable: Boolean) {
        viewModelScope.launch { repository.setDarkMode(enable) }
    }
}