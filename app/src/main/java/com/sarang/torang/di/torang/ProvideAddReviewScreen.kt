package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.addreview.compose.AddReviewScreen

@Composable
fun ProvideAddReviewScreen(navHostController: NavHostController) {
    val navController = rememberNavController()
    AddReviewScreen(
        galleryScreen = { color, onNext, onClose ->
            GalleryNavHost(onNext = onNext, onClose = { onClose.invoke(null) })
        },
        navController = navController,
        onRestaurant = { navController.navigate("addReview") },
        onShared = { navHostController.popBackStack() },
        onNext = { navController.navigate("selectRestaurant") },
        onClose = { navHostController.popBackStack() },
        onNotSelected = { navController.navigate("addReview") }
    )
}