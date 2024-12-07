package com.sarang.torang.di.torang

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.compose.GalleryNavHost
import com.sarang.torang.RootNavController
import com.sarang.torang.addreview.compose.ModReviewScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal fun provideModReviewScreen(
    navHostController: RootNavController,
): @Composable (NavBackStackEntry) -> Unit = { navBackStackEntry ->
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
            }, onBack = {
                navController.popBackStack()
            })
        },
        navController = navController,
        onRestaurant = { navController.popBackStack() },
        onShared = { navHostController.popBackStack() },
        onNext = { },
        onClose = { navHostController.popBackStack() },
        onNotSelected = { navController.popBackStack() }
    )
}