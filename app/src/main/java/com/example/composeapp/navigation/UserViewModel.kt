package com.example.composeapp.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel: ViewModel() {
    init {
        Log.d("UserViewModel",hashCode().toString())
    }
    var _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun login(name:String) {
        _name.value = name
    }

    fun logout() {
        _name.value=""
    }
}