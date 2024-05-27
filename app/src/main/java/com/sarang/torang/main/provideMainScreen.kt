package com.sarang.torang.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.di.torang.provideMyProfileScreenNavHost
import com.sarang.torang.feedScreenWithProfile
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.findinglinkmodules.di.finding_di.Finding
import com.sryang.torang.compose.AlarmScreen


fun provideMainScreen(
    rootNavController: RootNavController,
): @Composable () -> Unit = {
    val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
    ProvideMainDialog(
        dialogsViewModel = dialogsViewModel,
        navController = rootNavController
    ) {
        MainScreen(
            feedScreen = feedScreenWithProfile(
                rootNavController = rootNavController,
                dialogsViewModel
            ),
            onBottomMenu = {
                Log.d("__MainActivity", "onBottomMenu:${it}")
            },
            findingScreen = { Finding(navController = rootNavController) },
            myProfileScreen = provideMyProfileScreenNavHost(rootNavController),
            alarm = { AlarmScreen(onEmailLogin = {}) },
        )
    }
}