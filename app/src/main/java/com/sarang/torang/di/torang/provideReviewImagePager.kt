package com.sarang.torang.di.torang

import ZoomableTorangAsyncImage
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.navOptions
import com.sarang.torang.RootNavController
import com.sryang.imagepager.provideImagePager
import com.sryang.library.ExpandableText

internal fun provideReviewImagePager(
    rootNavController: RootNavController,
    onComment: (Int) -> Unit,
): @Composable (Int, Int) -> Unit =
    com.sryang.library.provideReviewImagePager(imagePager = provideImagePager(), image = { url ->
        ZoomableTorangAsyncImage(
            model = url,
            modifier = Modifier.fillMaxSize(),
            onSwipeDown = {
                rootNavController.popBackStack()
            }
        )
    },
        onComment = onComment,
        onLike = {
            rootNavController.like(it)
        },
        onDate = {
            Log.d("__provideReviewImagePager", "onDate is nothing")
        },
        onName = {
            rootNavController.profile(it)
        },
        onContents = {
            Log.d("__provideReviewImagePager", "onContents is nothing")
        },
        onPage = { Log.d("__provideReviewImagePager", "onPage is nothing") },
        expandableText = { modifier, text, expandableTextColor, onClickNickName ->
            ExpandableText(
                modifier = modifier,
                text = text,
                onClickNickName = onClickNickName,
                expandableTextColor = expandableTextColor
            )
        }
    )
