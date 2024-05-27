package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.feed.FeedScreenForMain
import com.sarang.torang.di.torang.provideProfileScreen
import com.sarang.torang.viewmodels.FeedDialogsViewModel

fun feedScreenWithProfile(
    feedNavController: NavHostController,
    rootNavController: RootNavController,
    dialogsViewModel: FeedDialogsViewModel,
    onTop: Boolean = false,
    consumeOnTop: () -> Unit = {},
): @Composable () -> Unit = {
    NavHost(navController = feedNavController, startDestination = "feed") {
        composable("feed") {
            FeedScreenForMain(
                onAddReview = { rootNavController.addReview() },
                feed = provideFeed(
                    { dialogsViewModel.onComment(it) },
                    { dialogsViewModel.onMenu(it) },
                    { dialogsViewModel.onShare(it) },
                    navController = feedNavController,
                    rootNavController = rootNavController
                ),
                consumeOnTop = consumeOnTop,
                onTop = onTop
            )
        }
        composable(
            route = "profile/{id}",
            content = provideProfileScreen(
                rootNavController = rootNavController,
                onClose = { feedNavController.popBackStack() })
        )
    }
}