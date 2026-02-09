package com.sarang.torang.di.likes

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sarang.library.LikeScreen
import com.sarang.torang.RootNavController
import com.sarang.torang.di.image.TorangAsyncImageData
import com.sarang.torang.di.image.provideTorangAsyncImage

fun provideLikeScreen(rootNavController: RootNavController): @Composable (Int) -> Unit =
    { reviewId ->
        LikeScreen(
            image = { modifier, model, progressSize, errorIconSize, contentScale -> provideTorangAsyncImage().invoke(TorangAsyncImageData(
                modifier = modifier,
                model = model,
                progressSize = progressSize ?: 30.dp,
                errorIconSize = errorIconSize?: 30.dp,
                contentScale = contentScale ?: ContentScale.None
            )) },
            reviewId = reviewId,
            onImage = rootNavController::profile,
            onName = rootNavController::profile,
            onBack = rootNavController::popBackStack
        )

    }