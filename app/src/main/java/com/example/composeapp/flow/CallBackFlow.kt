package com.example.composeapp.flow

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

enum class ConnectionState {
    Available,
    Unavailable
}

/**
 *   监听网络状态 改变文本
 *   flow。与。callbackFlow
 *   callbackFlow 把回调式API包装成Flow
 *
 *   flow 用emit   emit 是挂起函数
 *   callbackFlow    trySend  是非挂起函数。回调线程不能挂起，
 *                   它返回结果，数据先进入Channel缓冲
 *
 *
 *   callbackFlow 基于Channel实现
 *   awaitClose的作用是
 *   挂起代码块 ，不让它结束。Channel保持打开
 *   当Flow收集被取消时，执行清理逻辑
 */
class ConnectivityObserver(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val connectionState: Flow<ConnectionState> = callbackFlow @androidx.annotation.RequiresPermission(
        android.Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        val callback = object :ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(ConnectionState.Available)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(ConnectionState.Unavailable)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request,callback)

        // 设置初始状态
        val currentNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == false
        trySend(if(isConnected) ConnectionState.Available else ConnectionState.Unavailable)

        //取消收集时注销回调，防止内存泄漏
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged() //去重
}


@Composable
fun NetworkStatusScreen() {
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }
    val connectionState by connectivityObserver.connectionState.collectAsStateWithLifecycle(
        initialValue = ConnectionState.Unavailable
    )

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        when(connectionState) {
            ConnectionState.Available -> {
                Text("网络连接")
            }

            ConnectionState.Unavailable -> {
                Text("网络已断开，请检查网络")
            }
        }
    }
}