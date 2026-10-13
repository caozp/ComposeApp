package com.example.composeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel = UserViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true  //登陆成功后 清空返回栈
                    }
                }
            }, onGoRegister = {
                navController.navigate(Screen.Register.route)
            },viewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true  //登陆成功后 清空返回栈
                    }
                }
            }, onBack = {
                navController.popBackStack()
            },viewModel)
        }

        composable(Screen.Main.route) {
            MainScreen(onLogout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Main.route) {
                        inclusive = true  //登陆成功后 清空返回栈
                    }
                }
            },viewModel)
        }
    }
}