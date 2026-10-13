package com.example.composeapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class BottomTab(val route:String,val label: String,val icon: ImageVector) {
    object Home: BottomTab("tab_home","首页",Icons.Outlined.Home)
    object Mine: BottomTab("tab_mine","我的",Icons.Outlined.Person)
}
@Composable
fun MainScreen(onLogout:()->Unit,viewModel: UserViewModel) {
    val tabNavController = rememberNavController()
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val tabs = listOf(BottomTab.Home, BottomTab.Mine)
    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            tabNavController.navigate(tab.route) {
                                popUpTo(tabNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }, icon = { Icon(tab.icon,null)},
                        label = { Text(tab.label)}
                    )
                }

            }
        }
    ) { paddingValues ->
        NavHost(navController = tabNavController, startDestination = BottomTab.Home.route, modifier = Modifier.padding(paddingValues)) {
            composable(BottomTab.Home.route) { HomeItem(viewModel) }
            composable(BottomTab.Mine.route) { MineItem(onLogout,viewModel)}
        }

    }
}

@Composable
fun HomeItem(viewModel: UserViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val name = viewModel.name.collectAsStateWithLifecycle().value
        Text("主页面,欢迎$name")
    }
}

@Composable
fun MineItem(onLogout: () -> Unit,viewModel: UserViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            Text("我的")
            Button(onClick = {
                viewModel.logout()
                onLogout()
            }) {
                Text("退出登陆")
            }
        }
    }
}

