package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.feed.FeedScreenForMain
import com.sarang.torang.di.torang.provideProfileScreen
import com.sarang.torang.viewmodels.FeedDialogsViewModel

fun feedScreenWithProfile(
    rootNavController: RootNavController,
    dialogsViewModel: FeedDialogsViewModel,
): @Composable () -> Unit = {
    val feedNavHostController = rememberNavController()
    NavHost(navController = feedNavHostController, startDestination = "feed") {
        composable("feed") {
            FeedScreenForMain(
                onAddReview = { rootNavController.addReview() },
                feed = provideFeed(
                    { dialogsViewModel.onComment(it) },
                    { dialogsViewModel.onMenu(it) },
                    { dialogsViewModel.onShare(it) },
                    navController = feedNavHostController,
                    rootNavController = rootNavController
                ),
                consumeOnTop = {},
                onTop = false
            )
        }
        composable(
            route = "profile/{id}",
            content = provideProfileScreen(
                rootNavController = rootNavController,
                onClose = { feedNavHostController.popBackStack() })
        )
    }
}