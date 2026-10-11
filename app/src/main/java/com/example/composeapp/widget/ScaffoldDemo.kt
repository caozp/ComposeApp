package com.example.composeapp.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// 用一个stateHolder来保存Drawer的状态
class DrawerStateHolder (
    val drawerState: DrawerState,
    val scope: CoroutineScope,
    val navController: NavController,
    initialIndex:Int = 0
) {
    var selectedIndex by mutableIntStateOf(initialIndex)

    fun openDrawer() {
        scope.launch {
            drawerState.open()
        }
    }

    fun closeDrawer() {
        scope.launch {
            drawerState.close()
        }
    }

    fun selectItem(index: Int) {
        selectedIndex = index
    }
}


@Preview
@Composable
fun scaffoldPrevew() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val stateHolder = remember {
        DrawerStateHolder(drawerState, scope, navController,0)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    ModalNavigationDrawer(
        drawerState = stateHolder.drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer", style = MaterialTheme.typography.titleLarge)
                NavigationDrawerItem(
                    label = {
                        Text("主页")
                    },
                    selected = false,
                    onClick = {
                        stateHolder.closeDrawer()
                    },
                    icon = {
                        Icon(Icons.Default.Home,null)
                    }
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState)},
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = {
                        Text("首页")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                stateHolder.openDrawer()
                            }
                        ) {
                            Icon(Icons.Filled.Menu,null)
                        }
                    },
                    actions = {
                        var extend by remember { mutableStateOf(false) }
                        Icon(Icons.Default.Search,null, modifier = Modifier.padding(5.dp))
                        Box {
                            IconButton(onClick = {
                                extend = true
                            }) {
                                Icon(
                                    Icons.Default.MoreHoriz,
                                    null,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            showDropMenu(extend, { extend = false })
                        }
                    }
                )
            },
            bottomBar = {
                //监听当前路由
                val navBackStackEntry by stateHolder.navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                NavigationBar() {
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = {
                            stateHolder.navController.navigate("home") {
                                popUpTo(stateHolder.navController.graph.findStartDestination().id) {
                                    //每次切换都弹出到起始页，避免返回栈无限堆积。saveState 保存离开页面的状态
                                    saveState = true
                                }
                                //避免重复点击同一个tab时创建多个实例
                                launchSingleTop = true
                                //恢复之前状态
                                restoreState = true
                            }
                        }, icon = {
                            Icon(
                                if (currentRoute == "home")
                                    Icons.Filled.Home
                                else
                                    Icons.Outlined.Home,
                                null
                            )
                        }, label = {
                            Text("Home")
                        }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "setting",
                        onClick = {
                            stateHolder.navController.navigate("setting") {
                                popUpTo(stateHolder.navController.graph.findStartDestination().id) {
                                    //每次切换都弹出到起始页，避免返回栈无限堆积。saveState 保存离开页面的状态
                                    saveState = true
                                }
                                //避免重复点击同一个tab时创建多个实例
                                launchSingleTop = true
                                //恢复之前状态
                                restoreState = true
                            }
                        }, icon = {
                            Icon(
                                if (currentRoute == "setting")
                                    Icons.Filled.Settings
                                else
                                    Icons.Outlined.Settings,
                                null
                            )
                        }, label = {
                            Text("Settings")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "favorite",
                        onClick = {
                            stateHolder.navController.navigate("favorite") {
                                popUpTo(stateHolder.navController.graph.findStartDestination().id) {
                                    //每次切换都弹出到起始页，避免返回栈无限堆积。saveState 保存离开页面的状态
                                    saveState = true
                                }
                                //避免重复点击同一个tab时创建多个实例
                                launchSingleTop = true
                                //恢复之前状态
                                restoreState = true
                            }
                        }, icon = {
                            Icon(
                                if (currentRoute == "favorite")
                                    Icons.Filled.Favorite
                                else
                                    Icons.Outlined.FavoriteBorder,
                                null
                            )
                        }, label = {
                            Text("Favorite")
                        }
                    )
                }




            }
        ) { paddingValues ->
            NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(paddingValues)) {
                composable("home") { HomeItem()}
                composable("setting") { SettingsItem(scope,snackbarHostState)}
                composable("favorite") { FavoriteItem()}
            }
        }
    }
}

@Composable
fun HomeItem() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("主页面")
    }
}


@Composable
fun SettingsItem(scope: CoroutineScope,snackbarHostState: SnackbarHostState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("设置", modifier = Modifier.clickable(onClick = {
            scope.launch {
                snackbarHostState.showSnackbar("操作成功")
            }
        }))
    }
}

@Composable
fun FavoriteItem() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("喜欢的")
    }
}


@Composable
fun showDropMenu(extend: Boolean,onDismiss:()->Unit) {
    DropdownMenu(extend,  {
        onDismiss()
    }) {
        DropdownMenuItem(text = {
            Text("扫一扫")
        }, onClick = {
            onDismiss()
        })
    }

}