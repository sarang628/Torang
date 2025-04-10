package com.sarang.torang.di.torang

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.TorangScreen
import com.sarang.torang.repository.ChatRepository
import com.sryang.library.pullrefresh.rememberPullToRefreshState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProvideTorangScreen(chatRepository: ChatRepository) {
    val state = rememberPullToRefreshState()
    val rootNavController = RootNavController(rememberNavController())
    var reviewId: Int? by remember { mutableStateOf(null) }
    TorangScreen(
        rootNavController = rootNavController,
        mainScreen = provideMainScreen(rootNavController, state, chatRepository),
        profileScreen = provideProfileScreen(rootNavController, chatRepository),
        settingsScreen = provideSettingScreen(rootNavController),
        splashScreen = provideSplashScreen(rootNavController),
        addReviewScreen = provideAddReviewScreen(rootNavController),
        loginScreen = provideLoginNavHost(rootNavController),
        editProfileScreen = provideEditProfileScreen(rootNavController),
        editProfileImageScreen = provideGalleryNavHost(rootNavController),
        restaurantScreen = provideRestaurantNavScreen(rootNavController),
        modReviewScreen = provideModReviewScreen(rootNavController),
        emailLoginScreen = provideEmailLoginNavHost(rootNavController),
        imagePagerScreen = provideReviewImagePager(rootNavController, onComment = {
            reviewId = it
        }),
        restaurantImagePagerScreen = provideRestaurantImagePager(rootNavController, onComment = {
            reviewId = it
        }),
        likesScreen = provideLikeScreen(rootNavController),
        feedScreen = provideFeedScreen(rootNavController, state, reviewId ?: 0)
    )
}