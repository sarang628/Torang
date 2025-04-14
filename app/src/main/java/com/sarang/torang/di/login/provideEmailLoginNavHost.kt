package com.sarang.torang.di.login

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.signinsignup.LoginNavHost

internal fun provideEmailLoginNavHost(rootNavController: RootNavController): @Composable () -> Unit =
    {
        LoginNavHost(
            onSuccessLogin = rootNavController::popBackStack,
            onLookAround = {},
            showTopBar = true,
            onBack = rootNavController::popBackStack,
            showLookAround = false
        )
    }

