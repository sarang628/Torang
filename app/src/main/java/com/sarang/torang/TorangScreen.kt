package com.sarang.torang

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
    settings: @Composable () -> Unit,
    splashScreen: @Composable () -> Unit,
    addReviewScreen: @Composable () -> Unit,
    modReviewScreen: @Composable (NavBackStackEntry) -> Unit,
    loginScreen: @Composable () -> Unit,
    restaurantScreen: @Composable (NavBackStackEntry) -> Unit,
    editProfileScreen: @Composable () -> Unit,
    editProfileImageScreen: @Composable () -> Unit,
    mainScreen: @Composable () -> Unit,
    emailLoginScreen: @Composable () -> Unit,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
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
            settings.invoke()
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
        composable("myFeed/{reviewId}"){
            myFeed.invoke(it)
        }
    }
}