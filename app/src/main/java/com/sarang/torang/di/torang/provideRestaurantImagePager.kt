package com.sarang.torang.di.torang

import ZoomableTorangAsyncImage
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sarang.torang.RootNavController
import com.sryang.imagepager.provideImagePager

internal fun provideRestaurantImagePager(rootNavController: RootNavController): @Composable (Int) -> Unit =
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
        onComment = {
            Log.d("__provideRestaurantImagePager", "onComment is nothing")
        },
        onLike = {
            rootNavController.like(it)
        },
        onDate = {
            Log.d("__provideRestaurantImagePager", "onDate is nothing")
        },
        onName = {
            Log.d("__provideRestaurantImagePager", "onName is nothing")
        },
        onContents = {
            Log.d("__provideRestaurantImagePager", "onContents is nothing")
        })