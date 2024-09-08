package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.RootNavController

internal fun provideGalleryNavHost(rootNavController: RootNavController): @Composable () -> Unit = {
    GalleryNavHost(onNext = {
        //profileViewModel.updateProfileImage(it[0]) // TODO:: profileViewModel 없이 이미지 업로드 방법 찾기
        rootNavController.popBackStack()
    }, onClose = {
        rootNavController.popBackStack()
    }, onBack = {
        rootNavController.popBackStack()
    })
}