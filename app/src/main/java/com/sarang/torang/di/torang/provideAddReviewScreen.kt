package com.sarang.torang.di.torang

import android.os.Build
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.addreview.compose.AddReviewScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun provideAddReviewScreen(navHostController: RootNavController): @Composable (onClose: () -> Unit) -> Unit =
    { onCloseReview ->
        val navController = rememberNavController()
        val dispatch = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
        AddReviewScreen(
            galleryScreen = { color, onNext, onClose ->
                /*GalleryNavHost(onNext = onNext, onClose = { onClose.invoke(null) }, onBack = { onClose.invoke(null) })*/
            },
            navController = navController,
            onRestaurant = { navController.navigate("addReview") },
            onShared = { navHostController.popBackStack() },
            onNext = { navController.navigate("selectRestaurant") },
            onClose = { onCloseReview.invoke() },
            onNotSelected = { navController.navigate("addReview") },
            onBack = {
                dispatch?.onBackPressed()
            },
            onLogin = { navHostController.emailLogin() }
        )
    }