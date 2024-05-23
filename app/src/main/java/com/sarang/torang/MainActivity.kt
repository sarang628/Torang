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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_map.compose.MapScreenForRestaurant
import com.example.screen_map.data.MarkerData
import com.example.torangscreensettings.compose.SettingsScreen
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.addreview.compose.ModReviewScreen
import com.sarang.torang.compose.LoginNavHost
import com.sarang.torang.compose.MainMyFeedScreen
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.compose.ProfileScreenNavHost
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.Feeds
import com.sarang.torang.compose.feed.MainFeedScreen
import com.sarang.torang.compose.feed.MyFeedScreen
import com.sarang.torang.compose.restaurant.RestaurantNavScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.feed_di.review
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.di.torang.ProvideAddReviewScreen
import com.sarang.torang.di.torang.ProvideImagePager
import com.sarang.torang.di.torang.ProvideSplashScreen
import com.sarang.torang.uistate.FeedsUiState
import com.sryang.findinglinkmodules.di.finding_di.Finding
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
                    val rootNavController = rememberNavController()
                    TorangScreen(
                        navController = rootNavController,
                        mainScreen = provideMainScreen(rootNavController),
                        profileScreen = provideProfileScreen(rootNavController),
                        settingsScreen = provideSettingScreen(rootNavController),
                        splashScreen = { ProvideSplashScreen(rootNavController) },
                        addReviewScreen = { ProvideAddReviewScreen(rootNavController) },
                        loginScreen = { ProvideLoginNavHost(rootNavController) },
                        editProfileScreen = { ProvideEditProfileScreen(rootNavController) },
                        editProfileImageScreen = { ProvideGalleryNavHost(rootNavController) },
                        restaurantScreen = { ProvideRestaurantNavScreen(it, this) },
                        modReviewScreen = {
                            ProvideModReviewScreen(
                                navHostController = rootNavController,
                                navBackStackEntry = it
                            )
                        },
                        emailLoginScreen = { ProvideEmailLoginNavHost(rootNavController) },
                        imagePagerScreen = { reviewId, position ->
                            ProvideImagePager(
                                position = position,
                                reviewId = reviewId
                            )
                        }
                    )
                }
            }
        }
    }
}

private fun provideMainScreen(rootNavController: NavHostController): @Composable () -> Unit = {
    var commentDialogShow by remember { mutableStateOf(false) }
    var consumingBottomMenu by remember { mutableStateOf("") }
    val feedNavController = rememberNavController()
    MainScreen(
        onBottomMenu = { consumingBottomMenu = it },
        commentBottomSheet = provideCommentBottomDialogSheet(commentDialogShow) {
            commentDialogShow = false
        },
        menuDialog = provideFeedMenuBottomSheetDialog(),
        shareDialog = provideShareBottomSheetDialog(),
        reportDialog = provideReportModal(),
        onEdit = { rootNavController.navigate("modReview/${it}") },
        alarm = { AlarmScreen(onEmailLogin = {}) },
        findingScreen = { Finding(navController = rootNavController) },
        myProfileScreen = { ProvideMyProfileScreenNavHost(rootNavController = rootNavController) },
        feedScreen = provideFeedScreen(
            feedNavController = feedNavController,
            rootNavController = rootNavController,
            navController = rootNavController,
            onShowComment = { commentDialogShow = true },
            onConsumeCurrentBottomMenu = { consumingBottomMenu = "" },
            currentBottomMenu = consumingBottomMenu,
            profile = { ProvideScreenNavHost(it, feedNavController, rootNavController) },
            onImage = { reviewId, position -> rootNavController.navigate("imagePager/${reviewId}/${position}") }
        )
    )
}


@Composable
private fun ProvideScreenNavHost(
    navBackStackEntry: NavBackStackEntry,
    feedNavController: NavHostController,
    rootNavController: NavHostController,
) {
    ProfileScreenNavHost(
        id = navBackStackEntry.arguments?.getString("id")?.toInt(),
        onClose = { feedNavController.popBackStack() },
        onEmailLogin = { rootNavController.navigate("emailLogin") },
        onReview = { feedNavController.navigate("myFeed/${it}") },
        myFeed = {
            ProvideMyFeedScreen(
                navController = rootNavController,
                reviewId = it.arguments?.getString("reviewId")?.toInt()
                    ?: 0,
                onEdit = { rootNavController.navigate("modReview/${it}") },
                onProfile = { feedNavController.navigate("profile/${it}") },
                onBack = { feedNavController.popBackStack() },
                onRestaurant = { rootNavController.navigate("restaurant/${it}") }
            )
        },
        image = provideTorangAsyncImage()
    )
}

@Composable
private fun ProvideMyProfileScreenNavHost(rootNavController: NavHostController) {
    val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
    MyProfileScreenNavHost(
        navController = profileNavController,
        onSetting = { rootNavController.navigate("settings") },
        onEmailLogin = { rootNavController.navigate("emailLogin") },
        onReview = {
            Log.d("__Main", "MyProfileScreen onReview reviewId : ${it}")
            profileNavController.navigate("myFeed/${it}")
        },
        onClose = { profileNavController.popBackStack() },
        myFeed = {
            ProvideMyFeedScreen(/*myProfileScreen*/
                navController = rootNavController,
                reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0,
                onEdit = { rootNavController.navigate("modReview/${it}") },
                onProfile = { profileNavController.navigate("profile/${it}") },
                onBack = { profileNavController.popBackStack() }
            )
        }
    )
}

private fun provideProfileScreen(rootNavController: NavHostController): @Composable (NavBackStackEntry) -> Unit =
    {
        val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
        val userId = it.arguments?.getString("id")?.toInt()
        if (userId != null) {
            ProfileScreenNavHost(
                id = userId,
                onClose = { rootNavController.popBackStack() },
                onEmailLogin = { rootNavController.navigate("emailLogin") },
                onReview = { profileNavController.navigate("myFeed/${it}") },
                myFeed = {
                    ProvideMyFeedScreen(/*profileScreen*/
                        navController = profileNavController,
                        reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0,
                        onEdit = { rootNavController.navigate("modReview/${it}") }
                    )
                },
                image = provideTorangAsyncImage()
            )
        } else {
            Text(text = "사용자 정보가 없습니다.")
        }
    }

@Composable
private fun ProvideLoginNavHost(rootNavController: NavHostController) {
    LoginNavHost(
        onSuccessLogin = {
            rootNavController.navigate("main") {
                popUpTo(0)
            }
        },
        onLookAround = {
            rootNavController.navigate("main") {
                popUpTo(0)
            }
        })
}

@Composable
private fun ProvideEditProfileScreen(rootNavController: NavHostController) {
    EditProfileScreen(
        onEditImage = { rootNavController.navigate("EditProfileImage") },
        onBack = {
            rootNavController.popBackStack()
        },
        image = provideTorangAsyncImage()
    )
}

@Composable
private fun ProvideGalleryNavHost(rootNavController: NavHostController) {
    GalleryNavHost(onNext = {
        //profileViewModel.updateProfileImage(it[0]) // TODO:: profileViewModel 없이 이미지 업로드 방법 찾기
        rootNavController.popBackStack()
    }, onClose = {
        rootNavController.popBackStack()
    })
}

@Composable
private fun ProvideRestaurantNavScreen(
    navBackStackEntry: NavBackStackEntry,
    activity: ComponentActivity,
) {
    val restaurantId = navBackStackEntry.arguments?.getString("restaurantId")
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
                activity.startActivity(
                    Intent(Intent.ACTION_DIAL).apply {
                        setData(Uri.parse("tel:$number"))
                    }
                )
            },
            onWeb = { url ->
                activity.startActivity(
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
            },
            image = provideTorangAsyncImage(),
            onImage = {

            }
        )
    }
}

@Composable
fun ProvideModReviewScreen(
    navHostController: NavHostController,
    navBackStackEntry: NavBackStackEntry? = null,
) {
    val navController = rememberNavController()
    ModReviewScreen(
        reviewId = navBackStackEntry?.arguments?.getString("reviewId")?.toInt() ?: 0,
        galleryScreen = { color, onNext, onClose ->
            GalleryNavHost(onNext = {
                onNext.invoke(it)
                navController.popBackStack()
            }, onClose = {
                //onClose.invoke(null)
                navController.popBackStack()
            })
        },
        navController = navController,
        onRestaurant = { navController.popBackStack() },
        onShared = { navHostController.popBackStack() },
        onNext = { },
        onClose = { navHostController.popBackStack() },
        onNotSelected = { navController.popBackStack() }
    )
}

@Composable
fun ProvideEmailLoginNavHost(rootNavController: NavHostController) {
    LoginNavHost(
        onSuccessLogin = { rootNavController.popBackStack() },
        onLookAround = {},
        showTopBar = true,
        onBack = { rootNavController.popBackStack() },
        showLookAround = false
    )
}

@Composable
fun ProvideMyFeedScreen(
    navController: NavHostController,
    reviewId: Int,
    onEdit: (Int) -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    onRestaurant: ((Int) -> Unit)? = null,
    progressTintColor: Color? = Color(0xffe6cc00),
) {
    var show by remember { mutableStateOf(false) }

    MainMyFeedScreen(
        myFeedScreen = provideMyFeedScreen(
            navController = navController,
            reviewId = reviewId,
            onShowComment = { show = true },
            onProfile = onProfile,
            onBack = onBack,
            progressTintColor = progressTintColor,
            onRestaurant = {
                if (onRestaurant == null)
                    Log.w("__ProvideMyFeedScreen", "onRestaurantListener is null")
                onRestaurant?.invoke(it)
            }
        ),
        commentBottomSheet = provideCommentBottomDialogSheet(show) { show = false },
        menuDialog = provideFeedMenuBottomSheetDialog(),
        shareDialog = provideShareBottomSheetDialog(),
        reportDialog = provideReportModal(),
        onEdit = onEdit
    )
}

fun provideMyFeedScreen(
    navController: NavHostController,
    reviewId: Int,
    progressTintColor: Color? = null,
    onImage: ((Int) -> Unit)? = null,
    onShowComment: () -> Unit,
    onRestaurant: (Int) -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    onBack: (() -> Unit)? = null,
): @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit)) -> Unit =
    { onComment, onMenu, onShare ->
        val listState = rememberLazyListState()
        var scrollEnabled by remember { mutableStateOf(true) }

        MyFeedScreen(
            reviewId = reviewId,
            onBack = onBack,
            listState = listState,
            feed = { feed ->
                Feed(
                    review = feed.review(onComment = {
                        onComment.invoke(feed.reviewId)
                        onShowComment.invoke()
                    },
                        onShare = { onShare.invoke(feed.reviewId) },
                        onMenu = { onMenu.invoke(feed.reviewId) },
                        onName = { onProfile?.invoke(feed.userId) },
                        onRestaurant = { onRestaurant.invoke(feed.restaurantId) },
                        onProfile = { onProfile?.invoke(feed.userId) }),
                    isZooming = { scrollEnabled = !it },
                    progressTintColor = progressTintColor,
                    image = provideTorangAsyncImage(),
                    onImage = { navController.navigate("imagePager/${feed.reviewId}/${it}") }
                )
            }
        )
    }

fun provideFeedScreen(
    navController: NavHostController,
    feedNavController: NavHostController,
    rootNavController: NavHostController,
    progressTintColor: Color? = Color(0xffe6cc00),
    onImage: ((Int, Int) -> Unit)? = null,
    onShowComment: () -> Unit,
    currentBottomMenu: String,
    onConsumeCurrentBottomMenu: () -> Unit,
    profile: @Composable (NavBackStackEntry) -> Unit,
): @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit), navBackStackEntry: NavBackStackEntry) -> Unit =
    { onComment, onMenu, onShare, navBackStackEntry ->
        var scrollEnabled by remember { mutableStateOf(true) }
        var onTop by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = currentBottomMenu) {
            if (currentBottomMenu == "feed") {

                if (feedNavController.currentDestination?.route != "mainFeed") {
                    feedNavController.popBackStack("mainFeed", inclusive = false)
                } else {
                    onTop = true
                }

                onConsumeCurrentBottomMenu.invoke()
            }
        }

        NavHost(navController = feedNavController, startDestination = "mainFeed") {
            composable("mainFeed") {
                MainFeedScreen(
                    onAddReview = { rootNavController.navigate("addReview") },
                    onTop = onTop,
                    consumeOnTop = { onTop = false },
                    feed = { feed ->
                        Feed(
                            review = feed.review(
                                onComment = {
                                    onComment.invoke(feed.reviewId)
                                    onShowComment.invoke()
                                },
                                onShare = { onShare.invoke(feed.reviewId) },
                                onMenu = { onMenu.invoke(feed.reviewId) },
                                onName = { feedNavController.navigate("profile/${feed.userId}") },
                                onRestaurant = { navController.navigate("restaurant/${feed.restaurantId}") },
                                onProfile = { feedNavController.navigate("profile/${feed.userId}") }
                            ),
                            isZooming = { scrollEnabled = !it },
                            progressTintColor = progressTintColor,
                            image = provideTorangAsyncImage(),
                            onImage = { onImage?.invoke(feed.reviewId, it) }
                        )
                    }
                )
            }
            composable("profile/{id}") {
                profile.invoke(it)
            }
            composable("myFeed/{reviewId}") {
                ProvideMyFeedScreen(/*provideFeedScreen*/
                    navController = navController,
                    reviewId = it.arguments?.getString("reviewId")?.toInt() ?: 0,
                    onEdit = { navController.navigate("modReview/${it}") },
                    onProfile = { feedNavController.navigate("profile/${it}") },
                    onBack = { feedNavController.popBackStack() }
                )
            }
        }
    }


fun provideSettingScreen(navController: NavHostController): @Composable () -> Unit = {
    SettingsScreen(onLogout = {
        navController.navigate("splash")
    }, onBack = {
        navController.popBackStack()
    })
}