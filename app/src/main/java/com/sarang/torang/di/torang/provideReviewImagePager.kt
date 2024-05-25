package com.sarang.torang.di.torang

import ZoomableTorangAsyncImage
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sryang.imagepager.provideImagePager

internal fun provideReviewImagePager(): @Composable (Int, Int) -> Unit =
    com.sryang.library.provideReviewImagePager(imagePager = provideImagePager(), image = { url ->
        ZoomableTorangAsyncImage(
            model = url,
            modifier = Modifier.fillMaxSize()
        )
    })
