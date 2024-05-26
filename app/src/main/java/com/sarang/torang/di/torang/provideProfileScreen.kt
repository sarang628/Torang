package com.sarang.torang.di.torang

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.ProfileScreenNavHost
import com.sarang.torang.di.image.provideTorangAsyncImage

internal fun provideProfileScreen(rootNavController: RootNavController): @Composable (NavBackStackEntry) -> Unit =
    {
        val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
        val userId = it.arguments?.getString("id")?.toInt()
        if (userId != null) {
            ProfileScreenNavHost(
                navController = profileNavController,
                id = userId,
                onClose = { /*rootNavController.popBackStack()*/ },
                onEmailLogin = { rootNavController.emailLogin() },
                onReview = { profileNavController.navigate("myFeed/${it}") },
                myFeed = provideMyFeedScreen(
                    /*profileScreen*/
                    rootNavController = rootNavController,
                    onProfile = { profileNavController.navigate("profile/${it}") },
                    onBack = { profileNavController.popBackStack() }
                ),
                image = provideTorangAsyncImage()
            )
        } else {
            Text(text = "사용자 정보가 없습니다.")
        }
    }
