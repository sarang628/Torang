package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage

fun provideFeed(
    onComment: ((Int) -> Unit),
    onMenu: ((Int) -> Unit),
    onShare: ((Int) -> Unit),
    navController: NavHostController,
    rootNavController: RootNavController,
): @Composable (Feed) -> Unit = { feed ->
    Feed(
        review = feed.toReview(),
        isZooming = { /*scrollEnabled = !it*/ },
        progressTintColor = Color(0xffe6cc00),
        image = provideTorangAsyncImage(),
        onComment = { onComment.invoke(feed.reviewId) },
        onShare = { onShare.invoke(feed.reviewId) },
        onMenu = { onMenu.invoke(feed.reviewId) },
        onName = { navController.navigate("profile/${feed.userId}") },
        onRestaurant = { rootNavController.restaurant(feed.restaurantId) },
        onImage = { rootNavController.imagePager(feed.reviewId, it) },
        onProfile = { navController.navigate("profile/${feed.userId}") }
    )
}