package com.sarang.torang.di.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.di.profile_di.ProfileScreen


@Composable
fun ProvideEditProfileImageScreen(navController: NavHostController) {
    GalleryNavHost(onNext = {
        //profileViewModel.updateProfileImage(it[0]) // TODO:: profileViewModel 없이 이미지 업로드 방법 찾기
        navController.popBackStack()
    }, onClose = {
        navController.popBackStack()
    })
}

@Composable
fun ProvideEditProfileScreen(navController: NavHostController) {
    EditProfileScreen(
        onEditImage = { navController.navigate("EditProfileImage") },
        onBack = {
            navController.popBackStack()
        }
    )
}

@Composable
fun ProvideProfileScreen(
    navBackStackEntry: NavBackStackEntry? = null,
    navController: NavHostController
) {
    ProfileScreen(
        onSetting = { navController.navigate("settings") },
        navBackStackEntry = navBackStackEntry,
        onClose = { navController.popBackStack() },
        onEmailLogin = {navController.navigate("emailLogin")}
    )
}