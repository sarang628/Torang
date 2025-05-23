package com.sarang.torang.di.login

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.signinsignup.LoginNavHost

internal fun provideLoginNavHost(rootNavController: RootNavController): @Composable () -> Unit = {
    LoginNavHost(
        onSuccessLogin = rootNavController::main,
        onLookAround = rootNavController::main
    )
}
