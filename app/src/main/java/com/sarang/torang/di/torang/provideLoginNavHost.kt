package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.LoginNavHost

internal fun provideLoginNavHost(rootNavController: RootNavController): @Composable () -> Unit = {
    LoginNavHost(
        onSuccessLogin = { rootNavController.main() },
        onLookAround = { rootNavController.main() })
}
