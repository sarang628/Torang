package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.MainMyFeedScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.report_di.provideReportModal
import com.sryang.torangbottomsheet.di.bottomsheet.provideShareBottomSheetDialog

fun provideMyFeedScreen(
    rootNavController: RootNavController,
    onProfile: ((Int) -> Unit)? = null,
    onBack: (() -> Unit)? = null,
): @Composable (NavBackStackEntry) -> Unit = { it ->
    val reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0
    var show by remember { mutableStateOf(false) }

    MainMyFeedScreen(
        myFeedScreen = {_,_,_->}/*provideMyFeedScreen(
            navController = rootNavController,
            reviewId = reviewId,
            onShowComment = { show = true },
            onProfile = onProfile,
            onBack = onBack,
            progressTintColor = Color(0xffe6cc00),
            onRestaurant = {
                rootNavController.restaurant(it)
            }
        )*/,
        commentBottomSheet = provideCommentBottomDialogSheet(show) { show = false },
        menuDialog = provideFeedMenuBottomSheetDialog(),
        shareDialog = provideShareBottomSheetDialog(),
        reportDialog = provideReportModal(),
        onEdit = rootNavController.modReview()
    )
}
