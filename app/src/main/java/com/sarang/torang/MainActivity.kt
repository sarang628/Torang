package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.di.torang.provideAddReviewScreen
import com.sarang.torang.di.torang.provideEditProfileScreen
import com.sarang.torang.di.torang.provideEmailLoginNavHost
import com.sarang.torang.di.torang.provideGalleryNavHost
import com.sarang.torang.di.torang.provideLoginNavHost
import com.sarang.torang.di.torang.provideModReviewScreen
import com.sarang.torang.di.torang.provideProfileScreen
import com.sarang.torang.di.torang.provideRestaurantImagePager
import com.sarang.torang.di.torang.provideRestaurantNavScreen
import com.sarang.torang.di.torang.provideReviewImagePager
import com.sarang.torang.di.torang.provideSettingScreen
import com.sarang.torang.di.torang.provideSplashScreen
import com.sarang.torang.main.provideMainScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val rootNavController = RootNavController(rememberNavController())
                    TorangScreen(
                        rootNavController = rootNavController,
                        mainScreen = provideMainScreen(rootNavController),
                        profileScreen =provideProfileScreen(rootNavController),
                        settingsScreen = provideSettingScreen(rootNavController),
                        splashScreen = provideSplashScreen(rootNavController),
                        addReviewScreen = provideAddReviewScreen(rootNavController),
                        loginScreen = provideLoginNavHost(rootNavController),
                        editProfileScreen = provideEditProfileScreen(rootNavController),
                        editProfileImageScreen = provideGalleryNavHost(rootNavController),
                        restaurantScreen = provideRestaurantNavScreen(this, rootNavController),
                        modReviewScreen = provideModReviewScreen(rootNavController),
                        emailLoginScreen = provideEmailLoginNavHost(rootNavController),
                        imagePagerScreen = provideReviewImagePager(),
                        restaurantImagePagerScreen = provideRestaurantImagePager()
                    )
                }
            }
        }
    }
}