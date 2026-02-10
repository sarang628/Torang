package com.sarang.torang

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.screen_map.compose.MapScreenSingleRestaurantMarker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sarang.torang.compose.MainScreenState
import com.sarang.torang.compose.feed.state.FeedScreenState
import com.sarang.torang.compose.feed.state.rememberFeedScreenState
import com.sarang.torang.compose.rememberMainScreenState
import com.sarang.torang.di.addreview_di.provideAddReviewScreen
import com.sarang.torang.di.addreview_di.provideModReviewScreen
import com.sarang.torang.di.chat_di.provideChatScreen
import com.sarang.torang.di.finding_di.FindState
import com.sarang.torang.di.finding_di.rememberFindState
import com.sarang.torang.di.gallery.provideGalleryNavHost
import com.sarang.torang.di.likes.provideLikeScreen
import com.sarang.torang.di.login.provideEmailLoginNavHost
import com.sarang.torang.di.login.provideLoginNavHost
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideAlarmScreen
import com.sarang.torang.di.main_di.provideFeedScreenByReviewId
import com.sarang.torang.di.main_di.provideFindScreenType
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.main_di.provideMyProfileScreenNavHost
import com.sarang.torang.di.main_di.provideProfileScreenNavHost
import com.sarang.torang.di.profile_di.provideEditProfileScreen
import com.sarang.torang.di.restaurant_detail_container_di.provideRestaurantDetailContainer
import com.sarang.torang.di.settings.provideSettingScreen
import com.sarang.torang.di.splash_di.provideSplashScreen
import com.sarang.torang.di.torangimagepager.provideRestaurantImagePager
import com.sarang.torang.di.torangimagepager.provideReviewImagePager
import com.sarang.torang.dialogsbox.compose.DialogsBoxViewModel
import com.sarang.torang.viewmodel.profile.FeedListViewModel
import com.sarang.torang.viewmodel.profile.MyFeedListViewModel
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ProvideTorangScreen()
    } } } }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProvideTorangScreen() {
    val state               : PullToRefreshLayoutState = rememberPullToRefreshState()
    val rootNavController   : RootNavController        = RootNavController(rememberNavController())
    var reviewId            : Int?                     by remember { mutableStateOf(null) }
    val cameraPositionState : CameraPositionState      = rememberCameraPositionState()
    val findState           : FindState                = rememberFindState()
    val dialogsViewModel    : DialogsBoxViewModel      = hiltViewModel()
    val feedScreenState     : FeedScreenState          = rememberFeedScreenState()
    val mainScreenState     : MainScreenState          = rememberMainScreenState()
    val myFeedListViewModel : MyFeedListViewModel      = hiltViewModel()
    val scope               : CoroutineScope           = rememberCoroutineScope()
    TorangScreen(
        rootNavController           = rootNavController,
        mainScreen                  = provideMainScreen(
        rootNavController           = rootNavController,
        dialogsViewModel            = dialogsViewModel,
        feedScreenState             = feedScreenState,
        mainScreenState             = mainScreenState,
        findState                   = findState,
        chatScreen                  = provideChatScreen(),
        findScreen                  = provideFindScreenType(findState, rootNavController),
        alarmScreen                 = provideAlarmScreen(rootNavController),
        myProfileScreen             = provideMyProfileScreenNavHost(rootNavController, myFeedListViewModel),
        addReviewScreenType         = provideAddReviewScreen(navHostController =  rootNavController),
        onProfile                   = { scope.launch { myFeedListViewModel.reload()}}),
        profileScreen               = provideProfileScreenNavHost(rootNavController),
        settingsScreen              = provideSettingScreen(rootNavController),
        splashScreen                = provideSplashScreen(rootNavController),
        loginScreen                 = provideLoginNavHost(rootNavController),
        editProfileScreen           = provideEditProfileScreen(rootNavController),
        editProfileImageScreen      = provideGalleryNavHost(rootNavController),
        restaurantScreen            = provideRestaurantDetailContainer(rootNavController) ,
        modReviewScreen             = provideModReviewScreen(rootNavController),
        emailLoginScreen            = provideEmailLoginNavHost(rootNavController),
        imagePagerScreen            = provideReviewImagePager(rootNavController, onComment = { reviewId = it }),
        restaurantImagePagerScreen  = provideRestaurantImagePager(rootNavController, onComment = { reviewId = it }),
        likesScreen                 = provideLikeScreen(rootNavController),
        feedScreenByReviewId        = provideFeedScreenByReviewId(rootNavController = rootNavController),
        myFeedScreenByReviewId      = { ProvideMyFeedScreen(rootNavController = rootNavController, reviewId = it) },
        mapScreen                   = { MapScreenSingleRestaurantMarker(cameraPositionState = cameraPositionState, restaurantId = it ?: -1) },
        alarmScreen                 = provideAlarmScreen(rootNavController)
    )
}