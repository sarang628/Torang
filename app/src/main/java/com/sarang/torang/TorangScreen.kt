package com.sarang.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * @param profileScreen 피드화면에서 프로필 클릭 시 이동하는 화면. 메인 하단 내 프로필과 관련없음.
 */
@Composable
fun TorangScreen(
    navController: NavHostController,
    profileScreen: @Composable (NavBackStackEntry) -> Unit,
    settingsScreen: @Composable () -> Unit,
    splashScreen: @Composable () -> Unit,
    addReviewScreen: @Composable () -> Unit,
    modReviewScreen: @Composable (NavBackStackEntry) -> Unit,
    loginScreen: @Composable () -> Unit,
    restaurantScreen: @Composable (NavBackStackEntry) -> Unit,
    editProfileScreen: @Composable () -> Unit,
    editProfileImageScreen: @Composable () -> Unit,
    mainScreen: @Composable () -> Unit,
    emailLoginScreen: @Composable () -> Unit,
    imagePagerScreen: @Composable (Int, Int) -> Unit,
) {
    NavHost(
        navController = navController, startDestination = "splash",
    ) {
        composable("main") {
            mainScreen.invoke()
        }
        composable("addReview") {
            addReviewScreen.invoke()
        }
        composable("restaurant/{restaurantId}") { backStackEntry ->
            restaurantScreen.invoke(backStackEntry)
        }
        composable("profile/{id}") {
            profileScreen.invoke(it)
        }
        composable("splash") {
            splashScreen.invoke()
        }
        composable("login") {
            loginScreen.invoke()
        }
        composable("settings") {
            settingsScreen.invoke()
        }
        composable("editProfile") {
            editProfileScreen.invoke()
        }
        composable("EditProfileImage") {
            editProfileImageScreen.invoke()
        }
        composable("modReview/{reviewId}") {
            modReviewScreen.invoke(it)
        }
        composable("emailLogin") {
            emailLoginScreen.invoke()
        }
        composable("imagePager/{reviewId}/{position}") {
            val reviewId = it.arguments?.getString("reviewId")?.toInt()
            val position = it.arguments?.getString("position")?.toInt()
            Log.d(
                "__TorangScreen",
                "navigate ImagePager : reviewId : $reviewId, position : $position"
            )
            if (reviewId != null)
                imagePagerScreen.invoke(reviewId, position ?: 0)
            else {
                Log.e("__TorangScreen", "reviewId is null")
            }
        }
    }
}