package com.example.composeapp.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

//顶层属性委托 确保全局唯一实例
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")