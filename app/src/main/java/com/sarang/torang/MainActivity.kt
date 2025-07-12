package com.sarang.torang

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.di.addreview_di.provideAddReviewScreen
import com.sarang.torang.di.addreview_di.provideModReviewScreen
import com.sarang.torang.di.gallery.provideGalleryNavHost
import com.sarang.torang.di.likes.provideLikeScreen
import com.sarang.torang.di.login.provideEmailLoginNavHost
import com.sarang.torang.di.login.provideLoginNavHost
import com.sarang.torang.di.main_di.ProvideMainScreen
import com.sarang.torang.di.main_di.provideFeedScreen
import com.sarang.torang.di.profile_di.provideEditProfileScreen
import com.sarang.torang.di.profile_di.provideProfileScreen
import com.sarang.torang.di.settings.provideSettingScreen
import com.sarang.torang.di.splash_di.provideSplashScreen
import com.sarang.torang.di.torangimagepager.provideRestaurantImagePager
import com.sarang.torang.di.torangimagepager.provideReviewImagePager
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ProvideTorangScreen()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProvideTorangScreen() {
    val state = rememberPullToRefreshState()
    val rootNavController = RootNavController(rememberNavController())
    var reviewId: Int? by remember { mutableStateOf(null) }
    TorangScreen(
        rootNavController = rootNavController,
        mainScreen = { ProvideMainScreen(rootNavController) },
        profileScreen = provideProfileScreen(rootNavController),
        settingsScreen = provideSettingScreen(rootNavController),
        splashScreen = provideSplashScreen(rootNavController),
        addReviewScreen = provideAddReviewScreen(rootNavController),
        loginScreen = provideLoginNavHost(rootNavController),
        editProfileScreen = provideEditProfileScreen(rootNavController),
        editProfileImageScreen = provideGalleryNavHost(rootNavController),
        restaurantScreen = {  },
        modReviewScreen = provideModReviewScreen(rootNavController),
        emailLoginScreen = provideEmailLoginNavHost(rootNavController),
        imagePagerScreen = provideReviewImagePager(rootNavController, onComment = { reviewId = it }),
        restaurantImagePagerScreen = provideRestaurantImagePager(rootNavController, onComment = { reviewId = it }),
        likesScreen = provideLikeScreen(rootNavController),
        feedScreenByReviewId = provideFeedScreen(rootNavController, state, reviewId ?: 0)
    )
}