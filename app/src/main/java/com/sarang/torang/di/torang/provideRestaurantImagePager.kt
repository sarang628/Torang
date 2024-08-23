package com.sarang.torang.di.torang

import ZoomableTorangAsyncImage
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sarang.torang.RootNavController
import com.sryang.imagepager.provideImagePager
import com.sryang.library.ExpandableText

internal fun provideRestaurantImagePager(
    rootNavController: RootNavController,
    onComment: (Int) -> Unit,
): @Composable (Int) -> Unit =
    com.sryang.library.provideRestaurantImagePager(
        imagePager = provideImagePager(),
        image = { url ->
            ZoomableTorangAsyncImage(
                model = url,
                modifier = Modifier.fillMaxSize(),
                onSwipeDown = {
                    Log.d("__provideRestaurantImagePager", "onSwipeDown is nothing")
                    rootNavController.popBackStack()
                }
            )
        },
        onComment = onComment,
        onLike = { rootNavController.like(it) },
        onDate = {
            Log.d("__provideRestaurantImagePager", "onDate is nothing")
        },
        onName = { rootNavController.profile(it) },
        onContents = {
            Log.d("__provideRestaurantImagePager", "onContents is nothing")
        },
        expandableText = { modifier, text, expandableTextColor, onClickNickName ->
            ExpandableText(
                modifier = modifier,
                text = text,
                onClickNickName = onClickNickName,
                expandableTextColor = expandableTextColor
            )
        }
    )