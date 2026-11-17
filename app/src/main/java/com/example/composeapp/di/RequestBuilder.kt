package com.example.composeapp.di

import android.content.Context
import kotlinx.coroutines.delay


class RequestBuilder(context: Context) {

    suspend fun request() {
        delay(1500L)
    }
}