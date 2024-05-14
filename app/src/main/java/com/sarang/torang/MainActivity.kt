package com.sarang.torang

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.screen_map.compose.MapScreen
import com.example.screen_map.compose.MapScreenForRestaurant
import com.example.screen_map.data.MarkerData
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.compose.LoginNavHost
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.compose.ProfileScreenNavHost
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.compose.feed.Feeds
import com.sarang.torang.compose.restaurant.RestaurantNavScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideFeedScreen
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.di.torang.ProvideAddReviewScreen
import com.sarang.torang.di.torang.ProvideModReviewScreen
import com.sarang.torang.di.torang.ProvideSplashScreen
import com.sarang.torang.uistate.FeedsUiState
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
        var consumingBottomMenu by remember { mutableStateOf("") }
        TorangScreen(
            navController = navController,
            mainScreen = {
                val feedNavController = rememberNavController()
                MainScreen(
                    feedScreen = provideFeedScreen(
                        progressTintColor = Color(0xffe6cc00),
                        feedNavController = feedNavController,
                        navController = navController,
                        onAddReview = { navController.navigate("addReview") },
                        onShowComment = { commentDialogShow = true },
                        onConsumeCurrentBottomMenu = { consumingBottomMenu = "" },
                        currentBottomMenu = consumingBottomMenu,
                        profile = {
                            val userId = it.arguments?.getString("id")?.toInt()
                            ProfileScreenNavHost(
                                id = userId,
                                onClose = { feedNavController.popBackStack() },
                                onEmailLogin = { navController.navigate("emailLogin") },
                                onReview = { feedNavController.navigate("myFeed/${it}") },
                                myFeed = {
                                    ProvideMyFeedScreen(
                                        navController = navController,
                                        reviewId = it.arguments?.getString("reviewId")?.toInt()
                                            ?: 0,
                                        onEdit = { navController.navigate("modReview/${it}") },
                                        onProfile = { feedNavController.navigate("profile/${it}") },
                                        onBack = { feedNavController.popBackStack() },
                                        onRestaurant = { navController.navigate("restaurant/${it}") }
                                    )
                                },
                                image = provideTorangAsyncImage()
                            )
                        }
                    ),
                    onBottomMenu = {
                        Log.d("__MainActivity", "onBottomMenu:${it}")
                        consumingBottomMenu = it
                    },
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
                        },
                        image = provideTorangAsyncImage()
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
                    },
                    image = provideTorangAsyncImage()
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
            restaurantScreen = {
                val restaurantId = it.arguments?.getString("restaurantId")
                restaurantId?.let {
                    RestaurantNavScreen(restaurantId = it.toInt(),
                        feeds = {
                            Box {
                                Feeds(
                                    feedsUiState = FeedsUiState.Success(arrayListOf()),
                                    isRefreshing = false,
                                    onRefresh = { },
                                    onBottom = {},
                                    listState = rememberLazyListState()
                                )
                            }
                        },
                        onCall = { number ->
                            startActivity(
                                Intent(Intent.ACTION_DIAL).apply {
                                    setData(Uri.parse("tel:$number"))
                                }
                            )
                        },
                        onWeb = { url ->
                            startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            )
                        },
                        map = { title, lat, lon, foodType ->
                            Log.d("__MainActivity", "${title}, ${lat}, ${lon}, ${foodType}")
                            val markerData = MarkerData(
                                id = 0,
                                lat = lat,
                                lon = lon,
                                title = title,
                                foodType = foodType
                            )
                            MapScreenForRestaurant(
                                cameraPositionState = rememberCameraPositionState(),
                                selectedMarkerData = markerData
                            )
                        }
                    )
                }
            },
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