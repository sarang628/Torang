package com.sarang.torang.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.di.torang.provideMyProfileScreenNavHost
import com.sarang.torang.feedScreenWithProfile
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.findinglinkmodules.di.finding_di.Finding
import com.sryang.torang.compose.AlarmScreen
import kotlinx.coroutines.launch


fun provideMainScreen(
    rootNavController: RootNavController,
): @Composable () -> Unit = {
    val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
    val feedNavController = rememberNavController() // 메인 하단 홈버튼 클릭시 처리를 위해 여기에 설정
    var latestDestination by remember { mutableStateOf("feed") }
    var onTop by remember { mutableStateOf(false) }

    ProvideMainDialog(
        dialogsViewModel = dialogsViewModel,
        navController = rootNavController
    ) {
        MainScreen(
            feedScreen = feedScreenWithProfile(
                rootNavController = rootNavController,
                feedNavController = feedNavController,
                dialogsViewModel = dialogsViewModel,
                onTop = onTop,
                consumeOnTop = { onTop = false }
            ),
            onBottomMenu = {
                if (feedNavController.currentDestination?.route != "feed" && latestDestination == "feed" && it == "feed") {
                    // 피드 화면안에서 다른화면 상태일 때 피드 버튼을 눌렀다면 피드 화면으로 이동
                    feedNavController.popBackStack("feed", inclusive = false)
                } else if (latestDestination == "feed" && it == "feed") {
                    // 피드화면에서 피드 버튼을 눌렀을 때 리스트 최상단 이동
                    onTop = true
                }
                latestDestination = it
                Log.d("__provideMainScreen", "onBottomMenu:${it}")
            },
            findingScreen = { Finding(navController = rootNavController) },
            myProfileScreen = provideMyProfileScreenNavHost(rootNavController),
            alarm = { AlarmScreen(onEmailLogin = {}) },
        )
    }
}