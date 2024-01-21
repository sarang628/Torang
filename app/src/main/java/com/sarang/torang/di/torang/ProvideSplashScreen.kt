package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.sarang.torang.di.util.singleTop
import com.sryang.splash.compose.SplashScreen
import com.sryang.splash.uistate.LoginState

@Composable
fun ProvideSplashScreen(navHostController: NavHostController) {
    SplashScreen(onLoginState = {
        when (it) {
            LoginState.SESSION_EXPIRED -> {
                navHostController.singleTop("login")
            }

            LoginState.LOGIN -> {
                navHostController.singleTop("main")
            }

            LoginState.LOGOUT -> {
                navHostController.singleTop("login")
            }

            else -> {

            }
        }
    })
}