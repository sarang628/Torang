package com.sarang.torang.di.torang

import android.util.Log
import androidx.compose.material3.Text
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
    navController: NavHostController,
    onReview: ((Int) -> Unit)? = null,
    onProfile: ((Int) -> Unit)? = null
) {
    val userId = navBackStackEntry?.arguments?.getString("id")?.toInt()
    if (userId != null) {
        ProfileScreen(
            onSetting = { navController.navigate("settings") },
            userId = userId,
            onClose = { navController.popBackStack() },
            onEmailLogin = { navController.navigate("emailLogin") },
            onReview = onReview,
            onProfile = onProfile
        )
    } else {
        Text(text = "사용자 정보가 없습니다.")
    }
}