package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.FeedScreenForMain
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.ProvideMyFeedScreen

fun provideFeedScreen(
    rootNavController: RootNavController,
    feedNavController: NavHostController,
    progressTintColor: Color = Color(0xffe6cc00),
    onImage: ((Int, Int) -> Unit)? = null,
    onShowComment: () -> Unit,
    currentBottomMenu: String,
    onConsumeCurrentBottomMenu: () -> Unit,
): @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit), navBackStackEntry: NavBackStackEntry) -> Unit =
    { onComment, onMenu, onShare, navBackStackEntry ->
        var scrollEnabled by remember { mutableStateOf(true) }
        var onTop by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = currentBottomMenu) {
            if (currentBottomMenu == "feed") {

                if (feedNavController.currentDestination?.route != "mainFeed") {
                    feedNavController.popBackStack("mainFeed", inclusive = false)
                } else {
                    onTop = true
                }

                onConsumeCurrentBottomMenu.invoke()
            }
        }

        NavHost(navController = feedNavController, startDestination = "mainFeed") {
            composable("mainFeed") {
                FeedScreenForMain(
                    onAddReview = { rootNavController.addReview() },
                    onTop = onTop,
                    consumeOnTop = { onTop = false },
                    feed = { feed, onLike, onFavorite ->
                        Feed(
                            review = feed.toReview(),
                            onComment = {
                                onComment.invoke(feed.reviewId)
                                onShowComment.invoke()
                            },
                            onShare = { onShare.invoke(feed.reviewId) },
                            onMenu = { onMenu.invoke(feed.reviewId) },
                            onName = { feedNavController.navigate("profile/${feed.userId}") },
                            onRestaurant = { rootNavController.restaurant(feed.restaurantId) },
                            onProfile = { feedNavController.navigate("profile/${feed.userId}") },
                            isZooming = { scrollEnabled = !it },
                            progressTintColor = progressTintColor,
                            image = provideTorangAsyncImage(),
                            onImage = { onImage?.invoke(feed.reviewId, it) },
                            onFavorite = { onFavorite.invoke(feed.reviewId) },
                            onLike = { onLike.invoke(feed.reviewId) },
                            onLikes = { rootNavController.like(feed.reviewId) }
                        )
                    }
                )
            }
            composable(
                "profile/{id}",
                content = { provideProfileScreenNavHost(feedNavController, rootNavController).invoke(it) }
            )
            composable("myFeed/{reviewId}", content = {
                ProvideMyFeedScreen(
                    rootNavController = rootNavController,
                    navController = feedNavController,
                    navBackStackEntry = it
                )
            })
        }
    }