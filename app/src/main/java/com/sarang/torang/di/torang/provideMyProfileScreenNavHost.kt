package com.sarang.torang.di.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost

internal fun provideMyProfileScreenNavHost(rootNavController: RootNavController): @Composable () -> Unit =
    {
        val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
        MyProfileScreenNavHost(
            navController = profileNavController,
            onSetting = { rootNavController.settings() },
            onEmailLogin = { rootNavController.emailLogin() },
            onReview = {
                Log.d("__Main", "MyProfileScreen onReview reviewId : ${it}")
                profileNavController.navigate("myFeed/${it}")
            },
            onClose = { profileNavController.popBackStack() },
            myFeed = {
                ProvideMyFeedScreen(
                    rootNavController = rootNavController,
                    navController = profileNavController,
                    navBackStackEntry = it
                )
            }

        )
    }
