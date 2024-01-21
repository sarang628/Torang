package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.GalleryNavHost
import com.sryang.addreview.compose.AddReviewScreen
import com.sryang.addreview.compose.ModReviewScreen

@Composable
fun ProvideModReviewScreen(
    navHostController: NavHostController,
    navBackStackEntry: NavBackStackEntry? = null,
) {
    val navController = rememberNavController()
    ModReviewScreen(
        reviewId = navBackStackEntry?.arguments?.getString("reviewId")?.toInt() ?: 0,
        galleryScreen = { color, onNext, onClose ->
            GalleryNavHost(onNext = {
                onNext.invoke(it)
                navController.popBackStack()
            }, onClose = {
                //onClose.invoke(null)
                navController.popBackStack()
            })
        },
        navController = navController,
        onRestaurant = { navController.popBackStack() },
        onShared = { navHostController.popBackStack() },
        onNext = { },
        onClose = { navHostController.popBackStack() }
    )
}