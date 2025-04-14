package com.sarang.torang.di.settings

import androidx.compose.runtime.Composable
import com.example.torangscreensettings.compose.SettingsScreen
import com.sarang.torang.RootNavController

fun provideSettingScreen(rootNavController: RootNavController): @Composable () -> Unit = {
    SettingsScreen(
        onLogout = rootNavController::splash,
        onBack = rootNavController::popBackStack
    )
}