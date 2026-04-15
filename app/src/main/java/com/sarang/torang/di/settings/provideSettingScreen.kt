package com.sarang.torang.di.settings

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.SettingsScreen

fun provideSettingScreen(rootNavController: RootNavController): @Composable () -> Unit = {
    SettingsScreen(
        onLogout = rootNavController::splash,
        onBack = rootNavController::popBackStack
    )
}