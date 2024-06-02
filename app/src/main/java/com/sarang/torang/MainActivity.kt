package com.sarang.torang

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.library.LikeScreen
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.torang.provideAddReviewScreen
import com.sarang.torang.di.torang.provideEditProfileScreen
import com.sarang.torang.di.torang.provideEmailLoginNavHost
import com.sarang.torang.di.torang.provideGalleryNavHost
import com.sarang.torang.di.torang.provideLikeScreen
import com.sarang.torang.di.torang.provideLoginNavHost
import com.sarang.torang.di.torang.provideModReviewScreen
import com.sarang.torang.di.torang.provideProfileScreen
import com.sarang.torang.di.torang.provideRestaurantImagePager
import com.sarang.torang.di.torang.provideRestaurantNavScreen
import com.sarang.torang.di.torang.provideReviewImagePager
import com.sarang.torang.di.torang.provideSettingScreen
import com.sarang.torang.di.torang.provideSplashScreen
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
                    var reviewId: Int? by remember { mutableStateOf(null) }
                    TorangScreen(
                        rootNavController = rootNavController,
                        mainScreen = provideMainScreen(rootNavController),
                        profileScreen = provideProfileScreen(rootNavController),
                        settingsScreen = provideSettingScreen(rootNavController),
                        splashScreen = provideSplashScreen(rootNavController),
                        addReviewScreen = provideAddReviewScreen(rootNavController),
                        loginScreen = provideLoginNavHost(rootNavController),
                        editProfileScreen = provideEditProfileScreen(rootNavController),
                        editProfileImageScreen = provideGalleryNavHost(rootNavController),
                        restaurantScreen = provideRestaurantNavScreen(this, rootNavController),
                        modReviewScreen = provideModReviewScreen(rootNavController),
                        emailLoginScreen = provideEmailLoginNavHost(rootNavController),
                        imagePagerScreen = provideReviewImagePager(rootNavController, onComment = {
                            Log.d("__provideRestaurantImagePager", "onComment is nothing")
                            reviewId = it
                        }),
                        restaurantImagePagerScreen = provideRestaurantImagePager(rootNavController),
                        likesScreen = provideLikeScreen(rootNavController)
                    )

                    reviewId?.let {
                        provideCommentBottomDialogSheet().invoke(it) { reviewId = null }
                    }
                }
            }
        }
    }
}