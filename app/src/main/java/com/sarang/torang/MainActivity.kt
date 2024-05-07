package com.sarang.torang

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.di.restaurant_detail.ProvideRestaurantScreen
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.compose.LoginNavHost
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.compose.ProfileScreenNavHost
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.feed_di.provideFeedScreen
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.di.torang.ProvideAddReviewScreen
import com.sarang.torang.di.torang.ProvideModReviewScreen
import com.sarang.torang.di.torang.ProvideSplashScreen
import com.sryang.findinglinkmodules.di.finding_di.Finding
import com.sryang.settings.di.settings.ProvideSettingScreen
import com.sryang.torang.compose.AlarmScreen
import com.sryang.torangbottomsheet.di.bottomsheet.provideShareBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    TorangScreen()
                }
            }
        }
    }

    @Composable
    fun TorangScreen() {
        val navController = rememberNavController()
        var commentDialogShow by remember { mutableStateOf(false) }
        TorangScreen(
            navController = navController,
            mainScreen = {
                MainScreen(
                    feedScreen = provideFeedScreen(
                        progressTintColor = Color(0xffe6cc00),
                        navController = navController,
                        onAddReview = { navController.navigate("addReview") },
                        onShowComment = { commentDialogShow = true }
                    ),
                    findingScreen = { Finding(navController = navController) },
                    myProfileScreen = {
                        val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
                        MyProfileScreenNavHost(
                            navController = profileNavController,
                            onSetting = { navController.navigate("settings") },
                            onEmailLogin = { navController.navigate("emailLogin") },
                            onReview = {
                                Log.d("__Main", "MyProfileScreen onReview reviewId : ${it}")
                                profileNavController.navigate("myFeed/${it}")
                            },
                            onClose = { profileNavController.popBackStack() },
                            myFeed = {
                                ProvideMyFeedScreen(/*myProfileScreen*/
                                    navController = navController,
                                    reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0,
                                    onEdit = { navController.navigate("modReview/${it}") },
                                    onProfile = { profileNavController.navigate("profile/${it}") },
                                    onBack = { profileNavController.popBackStack() }
                                )
                            }
                        )
                    },
                    alarm = { AlarmScreen(onEmailLogin = {}) },
                    commentBottomSheet = provideCommentBottomDialogSheet(commentDialogShow) {
                        commentDialogShow = false
                    },
                    menuDialog = provideFeedMenuBottomSheetDialog(),
                    shareDialog = provideShareBottomSheetDialog(),
                    reportDialog = provideReportModal(),
                    onEdit = { navController.navigate("modReview/${it}") }
                )
            },
            profileScreen = {
                Log.d("__TORANG", "move ProvideProfileScreen")
                val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
                val userId = it.arguments?.getString("id")?.toInt()
                if (userId != null) {
                    ProfileScreenNavHost(
                        id = userId,
                        onClose = { navController.popBackStack() },
                        onEmailLogin = { navController.navigate("emailLogin") },
                        onReview = { profileNavController.navigate("myFeed/${it}") },
                        myFeed = {
                            ProvideMyFeedScreen(/*profileScreen*/
                                navController = profileNavController,
                                reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0,
                                onEdit = { navController.navigate("modReview/${it}") }
                            )
                        }
                    )
                } else {
                    Text(text = "사용자 정보가 없습니다.")
                }
            },
            settings = { ProvideSettingScreen(navController) },
            splashScreen = { ProvideSplashScreen(navController) },
            addReviewScreen = { ProvideAddReviewScreen(navController) },
            loginScreen = {
                LoginNavHost(
                    onSuccessLogin = {
                        navController.navigate("main") {
                            popUpTo(0)
                        }
                    },
                    onLookAround = {
                        navController.navigate("main") {
                            popUpTo(0)
                        }
                    })
            },
            editProfileScreen = {
                EditProfileScreen(
                    onEditImage = { navController.navigate("EditProfileImage") },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            },
            editProfileImageScreen = {
                GalleryNavHost(onNext = {
                    //profileViewModel.updateProfileImage(it[0]) // TODO:: profileViewModel 없이 이미지 업로드 방법 찾기
                    navController.popBackStack()
                }, onClose = {
                    navController.popBackStack()
                })
            },
            restaurantScreen = { ProvideRestaurantScreen(it) },
            modReviewScreen = {
                ProvideModReviewScreen(
                    navHostController = navController,
                    navBackStackEntry = it
                )
            },
            emailLoginScreen = {
                LoginNavHost(
                    onSuccessLogin = { navController.popBackStack() },
                    onLookAround = {},
                    showTopBar = true,
                    onBack = { navController.popBackStack() },
                    showLookAround = false
                )
            }
        )
    }
}