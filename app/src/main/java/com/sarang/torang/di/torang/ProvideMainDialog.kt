package com.sarang.torang.di.torang

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.MainDialogs
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.di.report_di.provideShareBottomSheetDialog
import com.sarang.torang.viewmodels.FeedDialogsViewModel

@Composable
fun ProvideMainDialog(
    dialogsViewModel: FeedDialogsViewModel = hiltViewModel(),
    rootNavController: RootNavController,
    commentBottomSheet: @Composable (
        reviewId: Int?, onHidden: () -> Unit, content: @Composable (PaddingValues) -> Unit
    ) -> Unit,
    contents: @Composable (PaddingValues) -> Unit
) {
    val uiState by dialogsViewModel.uiState.collectAsState()
    Log.d("__ProvideMainDialog", "showComment: ${uiState.showComment}")
    MainDialogs(
        uiState = uiState,
        commentBottomSheet = { reviewId ->
            commentBottomSheet.invoke(reviewId, { dialogsViewModel.closeComment() }, contents)
        },
        menuDialog = { _, _, _, _, _ -> },
        shareDialog = provideShareBottomSheetDialog(),
        reportDialog = provideReportModal(),
        onEdit = rootNavController.modReview(),
    )
}
