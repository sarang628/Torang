package com.sarang.torang.di.torang

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.MainMyFeedScreen
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.MyFeedScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.torangbottomsheet.di.bottomsheet.provideShareBottomSheetDialog

fun provideMyFeedScreen(
    rootNavController: RootNavController,
    onProfile: ((Int) -> Unit)? = null,
    onBack: (() -> Unit)? = null,
): @Composable (NavBackStackEntry) -> Unit = { it ->
    val reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0
    var show by remember { mutableStateOf(false) }
    val viewModel: FeedDialogsViewModel = hiltViewModel()
    MainMyFeedScreen(
        myFeedScreen = provideMyFeedScreen(
            navController = rootNavController,
            reviewId = reviewId,
            onShowComment = { show = true },
            onProfile = onProfile,
            onBack = onBack,
            progressTintColor = Color(0xffe6cc00),
            onRestaurant = {
                rootNavController.restaurant(it)
            }
        ),
        commentBottomSheet = { provideCommentBottomDialogSheet().invoke(it, {}) },
        menuDialog = provideFeedMenuBottomSheetDialog(),
        shareDialog = provideShareBottomSheetDialog(),
        reportDialog = provideReportModal(),
        onEdit = rootNavController.modReview(),
        viewModel = viewModel
    )
}

fun provideMyFeedScreen(
    navController: RootNavController,
    reviewId: Int,
    progressTintColor: Color? = null,
    onImage: ((Int) -> Unit)? = null,
    onShowComment: () -> Unit,
    onRestaurant: (Int) -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    onBack: (() -> Unit)? = null,
): @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit)) -> Unit =
    { onComment, onMenu, onShare ->
        val listState = rememberLazyListState()
        var scrollEnabled by remember { mutableStateOf(true) }

        MyFeedScreen(
            reviewId = reviewId,
            onBack = onBack,
            listState = listState,
            feed = { feed ->
                Feed(
                    review = feed.toReview(),
                    onComment = {
                        onComment.invoke(feed.reviewId)
                        onShowComment.invoke()
                    },
                    onShare = { onShare.invoke(feed.reviewId) },
                    onMenu = { onMenu.invoke(feed.reviewId) },
                    onName = { onProfile?.invoke(feed.userId) },
                    onRestaurant = { onRestaurant.invoke(feed.restaurantId) },
                    onProfile = { onProfile?.invoke(feed.userId) },
                    isZooming = { scrollEnabled = !it },
                    progressTintColor = progressTintColor,
                    image = provideTorangAsyncImage(),
                    onImage = { navController.imagePager(feed.reviewId, it) }
                )
            }
        )
    }
