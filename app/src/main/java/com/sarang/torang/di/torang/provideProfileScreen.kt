package com.sarang.torang.di.torang

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.ProfileScreenNavHost
import com.sarang.torang.di.image.provideTorangAsyncImage

/**
 * @param onClose 메인화면 -> 피드 -> 프로필 진입 후 뒤로가기 버튼 클릭 시 RootNavController 가 아닌 다른 navController이 필요하여 추가
 */
internal fun provideProfileScreen(
    rootNavController: RootNavController,
    onClose: (() -> Unit)? = null,
): @Composable (NavBackStackEntry) -> Unit = {
    val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
    val userId = it.arguments?.getString("id")?.toInt()
    if (userId != null) {
        ProfileScreenNavHost(
            navController = profileNavController,
            id = userId,
            onClose = {
                if (onClose != null) {
                    onClose.invoke()
                } else {
                    rootNavController.popBackStack()
                }
            },
            onEmailLogin = { rootNavController.emailLogin() },
            onReview = { profileNavController.navigate("myFeed/${it}") },
            myFeed = provideMyFeedScreen(
                /*profileScreen*/
                rootNavController = rootNavController,
                onProfile = { profileNavController.navigate("profile/${it}") },
                onBack = { profileNavController.popBackStack() }
            ),
            image = provideTorangAsyncImage()
        )
    } else {
        Text(text = "사용자 정보가 없습니다.")
    }
}
