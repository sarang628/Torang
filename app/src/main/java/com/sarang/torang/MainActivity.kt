package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.di.restaurant_detail.ProvideRestaurantScreen
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.activity.go
import com.sarang.torang.compose.LoginNavHost
import com.sarang.torang.di.torang.ProvideAddReviewScreen
import com.sarang.torang.di.torang.ProvideEditProfileImageScreen
import com.sarang.torang.di.torang.ProvideEditProfileScreen
import com.sarang.torang.di.torang.ProvideModReviewScreen
import com.sarang.torang.di.torang.ProvideProfileScreen
import com.sarang.torang.di.torang.ProvideSplashScreen
import com.sarang.torang.di.util.singleTop
import com.sryang.settings.di.settings.ProvideSettingScreen
import com.sarang.torang.di.main_di.ProvideMainScreen
import com.sryang.findinglinkmodules.di.finding.Finding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Displaying edge-to-edge
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    TorangScreen()
                }
            }
        }
    }

    @Composable
    fun TorangScreen() {
        val navController = rememberNavController()
        TorangScreen(
            navController = navController,
            profileScreen = {
                ProvideProfileScreen(
                    navController = navController,
                    navBackStackEntry = it
                )
            },
            settings = { ProvideSettingScreen(navController) },
            splashScreen = { ProvideSplashScreen(navController) },
            addReviewScreen = { ProvideAddReviewScreen(navController) },
            loginScreen = {
                LoginNavHost(
                    onSuccessLogin = {
                        navController.navigate("main") {
                            popUpTo(0)
                        }
                    },
                    onLookAround = {
                        navController.navigate("main") {
                            popUpTo(0)
                        }
                    })
            },
            editProfileScreen = { ProvideEditProfileScreen(navController) },
            editProfileImageScreen = { ProvideEditProfileImageScreen(navController) },
            restaurantScreen = { ProvideRestaurantScreen(it) },
            mainScreen = {
                ProvideMainScreen(navController = navController,
                    myProfileScreen = {
                        ProvideProfileScreen(navController = navController)
                    },
                    findingScreen = { Finding(navController = navController) }
                )
            },
            modReviewScreen = {
                ProvideModReviewScreen(
                    navHostController = navController,
                    navBackStackEntry = it
                )
            },
            emailLoginScreen = {
                LoginNavHost(
                    onSuccessLogin = { navController.popBackStack() },
                    onLookAround = {},
                    showTopBar = true,
                    onBack = { navController.popBackStack() },
                    showLookAround = false
                )
            }
        )
    }
}

@Preview
@Composable
fun shimmerTest() {
    val showShimmer by remember { mutableStateOf(true) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), content = {
            items(2) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            shimmerBrush(showShimmer, 1300f)
                        )
                )
            }
        })
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}