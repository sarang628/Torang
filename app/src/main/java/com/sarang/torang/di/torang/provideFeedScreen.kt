package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.FeedScreenForMain
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState

fun provideFeedScreen(
    rootNavController: RootNavController,
    feedNavController: NavHostController,
    progressTintColor: Color = Color(0xffe6cc00),
    onImage: ((Int, Int) -> Unit)? = null,
    onShowComment: () -> Unit,
    currentBottomMenu: String,
    onConsumeCurrentBottomMenu: () -> Unit,
    videoPlayer: @Composable (url: String, isPlaying: Boolean, onVideoClick: () -> Unit) -> Unit,
): @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit), navBackStackEntry: NavBackStackEntry) -> Unit =
    { onComment, onMenu, onShare, navBackStackEntry ->
        var scrollEnabled by remember { mutableStateOf(true) }
        var onTop by remember { mutableStateOf(false) }
        val state = rememberPullToRefreshState()

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
                    shimmerBrush = { it -> shimmerBrush(it) },
                    feed = { feed, onLike, onFavorite, isLogin, onVideoClick, imageHeight ->
                        Feed(
                            review = feed.toReview(),
                            onComment = {
                                onComment.invoke(feed.reviewId)
                                onShowComment.invoke()
                            },
                            onShare = { if (isLogin) onShare.invoke(feed.reviewId) },
                            onMenu = { onMenu.invoke(feed.reviewId) },
                            onName = { feedNavController.navigate("profile/${feed.userId}") },
                            onRestaurant = { rootNavController.restaurant(feed.restaurantId) },
                            onProfile = { feedNavController.navigate("profile/${feed.userId}") },
                            isZooming = { scrollEnabled = !it },
                            progressTintColor = progressTintColor,
                            imageLoadCompose = provideTorangAsyncImage(),
                            onImage = { onImage?.invoke(feed.reviewId, it) },
                            onFavorite = { onFavorite.invoke(feed.reviewId) },
                            onLike = { onLike.invoke(feed.reviewId) },
                            onLikes = { rootNavController.like(feed.reviewId) },
                            expandableText = provideExpandableText(),
                            isLogin = isLogin,
                            videoPlayer = { videoPlayer.invoke(it, feed.isPlaying, onVideoClick) },
                            imageHeight = if (imageHeight > 0) imageHeight.dp else 600.dp
                        )
                    },
                    pullToRefreshLayout = { isRefreshing, onRefresh, contents ->

                        if (isRefreshing) {
                            state.updateState(RefreshIndicatorState.Refreshing)
                        } else {
                            state.updateState(RefreshIndicatorState.Default)
                        }

                        PullToRefreshLayout(
                            pullRefreshLayoutState = state,
                            refreshThreshold = 80,
                            onRefresh = onRefresh
                        ) {
                            contents.invoke()
                        }
                    }
                )
            }
            composable(
                "profile/{id}",
                content = {
                    provideProfileScreenNavHost(
                        feedNavController,
                        rootNavController,
                        videoPlayer
                    ).invoke(it)
                }
            )
            composable("myFeed/{reviewId}", content = {
                ProvideMyFeedScreen(
                    rootNavController = rootNavController,
                    navController = feedNavController,
                    navBackStackEntry = it,
                    videoPlayer = videoPlayer
                )
            })
        }
    }