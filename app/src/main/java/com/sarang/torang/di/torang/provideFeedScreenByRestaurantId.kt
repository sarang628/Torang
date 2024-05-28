package com.sarang.torang.di.torang

import android.provider.DocumentsContract.Root
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.viewmodels.FeedDialogsViewModel

fun provideFeedScreenByRestaurantId(navHostController: RootNavController): @Composable (Int) -> Unit =
    {
        val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
        ProvideMainDialog(navController = navHostController, dialogsViewModel = dialogsViewModel) {
            FeedScreenByRestaurantId(
                restaurantId = it,
                feed = { feed, onLike, onFavorite ->
                    Feed(
                        review = feed.toReview(),
                        image = provideTorangAsyncImage(),
                        onComment = { dialogsViewModel.onComment(feed.reviewId) },
                        onShare = { dialogsViewModel.onShare(feed.reviewId) },
                        onMenu = { dialogsViewModel.onMenu(feed.reviewId) },
                        onImage = { navHostController.imagePager(feed.reviewId, it) },
                        onLike = { onLike.invoke(feed.reviewId) },
                        onFavorite = { onFavorite.invoke(feed.reviewId) },
                        onRestaurant = {
                            Log.w("provideFeedScreenByRestaurantId", "onRestaurant is nothing")
                        },
                        onName = {
                            Log.w("provideFeedScreenByRestaurantId", "onName is nothing")
                        },
                        onProfile = {
                            Log.w("provideFeedScreenByRestaurantId", "onProfile is nothing")
                        },
                        isZooming = {
                            Log.w("provideFeedScreenByRestaurantId", "isZooming is nothing")
                        }
                    )
                }
            )
        }
    }