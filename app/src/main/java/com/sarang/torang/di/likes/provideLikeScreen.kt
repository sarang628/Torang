package com.sarang.torang.di.likes

import androidx.compose.runtime.Composable
import com.sarang.library.LikeScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.di.image.provideTorangAsyncImage

fun provideLikeScreen(rootNavController: RootNavController): @Composable (Int) -> Unit =
    { reviewId ->
        LikeScreen(
            image = provideTorangAsyncImage(),
            reviewId = reviewId,
            onImage = rootNavController::profile,
            onName = rootNavController::profile,
            onBack = rootNavController::popBackStack
        )

    }