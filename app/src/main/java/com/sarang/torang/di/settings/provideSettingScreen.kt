package com.sarang.torang.di.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.SettingsScreen

fun provideSettingScreen(rootNavController: RootNavController): @Composable () -> Unit = {
    Scaffold {
        Box(Modifier.padding(it)){
            SettingsScreen(
                onLogout = rootNavController::splash,
                onBack = rootNavController::popBackStack
            )
        }
    }
}