package com.sarang.torang.di.torangimagepager

import ZoomableTorangAsyncImage
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sarang.torang.RootNavController
import com.sryang.imagepager.provideImagePager
import com.sryang.library.ExpandableText
import com.sryang.library.provideReviewImagePager

@Composable
internal fun provideReviewImagePager(
    rootNavController: RootNavController,
    onComment: (Int) -> Unit = {},
): @Composable (Int, Int) -> Unit = { reviewId, position ->
    provideReviewImagePager(
        imagePager = provideImagePager(),
        image = { url ->
            ZoomableTorangAsyncImage(
                model = url,
                modifier = Modifier.fillMaxSize(),
                onSwipeDown = rootNavController::popBackStack
            )
        },
        onComment = onComment,
        onLike = rootNavController::like,
        onDate = { Log.d("__provideReviewImagePager", "onDate is nothing") },
        onName = { rootNavController::profile },
        onContents = { Log.d("__provideReviewImagePager", "onContents is nothing") },
        onPage = { Log.d("__provideReviewImagePager", "onPage is nothing") },
        expandableText = { modifier, text, expandableTextColor, onClickNickName ->
            ExpandableText(
                modifier = modifier,
                text = text,
                onClickNickName = onClickNickName,
                expandableTextColor = expandableTextColor
            )
        }
    ).invoke(reviewId, position)
}