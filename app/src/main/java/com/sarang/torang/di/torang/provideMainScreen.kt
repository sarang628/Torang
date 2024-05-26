package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.report_di.provideReportModal
import com.sryang.findinglinkmodules.di.finding_di.Finding
import com.sryang.torang.compose.AlarmScreen
import com.sryang.torangbottomsheet.di.bottomsheet.provideShareBottomSheetDialog

internal fun provideMainScreen(rootNavController: RootNavController): @Composable () -> Unit = {
    var commentDialogShow by remember { mutableStateOf(false) }
    var consumingBottomMenu by remember { mutableStateOf("") }
    val feedNavController = rememberNavController()
    MainScreen(
        onBottomMenu = { consumingBottomMenu = it },
        alarm = { AlarmScreen(onEmailLogin = {}) },
        findingScreen = { Finding(navController = rootNavController) },
        myProfileScreen = provideMyProfileScreenNavHost(rootNavController),
        feedScreen = provideFeedScreen(
            feedNavController = feedNavController,
            rootNavController = rootNavController,
            onShowComment = { commentDialogShow = true },
            onConsumeCurrentBottomMenu = { consumingBottomMenu = "" },
            currentBottomMenu = consumingBottomMenu,
            onImage = { reviewId, position -> rootNavController.imagePager(reviewId, position) }
        ),
        onShare = {},
        onMenu = {},
        onComment = {}
    )
}
