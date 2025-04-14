package com.sarang.torang.di.gallery

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController

internal fun provideGalleryNavHost(rootNavController: RootNavController): @Composable () -> Unit = {
    /*GalleryNavHost(onNext = {
        rootNavController.popBackStack()
    }, onClose = {
        rootNavController.popBackStack()
    }, onBack = {
        rootNavController.popBackStack()
    })*/
}