package com.example.composeapp.di

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeViewIntent {
    object Request: HomeViewIntent()
}

data class State(val show: Boolean,val msg:String)

@HiltViewModel
class HiltViewModel @Inject constructor(
    private val rb: RequestBuilder
): ViewModel() {

    private val _tipState = MutableStateFlow(State(show = false,""))
    var tipState = _tipState.asStateFlow()

//    init {
//        rb = RequestBuilder(application)
//    }

    fun sendIntent(homeIntent: HomeViewIntent) {
        when(homeIntent) {
            HomeViewIntent.Request -> startRequest()
        }
    }

    fun startRequest() = viewModelScope.launch(Dispatchers.IO) {
        _tipState.update {
            it.copy(show = true,msg = "request")
        }

        rb.request()

        _tipState.update {
            it.copy(msg = "success")
        }
    }


}