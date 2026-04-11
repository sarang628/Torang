package com.sarang.torang

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
import com.sarang.torang.di.login_di.provideEmailLoginNavHost
import com.sarang.torang.di.login_di.provideLoginNavHost
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideAlarmScreen
import com.sarang.torang.di.main_di.provideFeedScreenByReviewId
import com.sarang.torang.di.main_di.provideFindScreenType
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.main_di.provideMyProfileScreenNavHost
import com.sarang.torang.di.main_di.provideProfileScreenNavHost
import com.sarang.torang.di.map_di.provideMapScreen
import com.sarang.torang.di.profile_di.provideEditProfileScreen
import com.sarang.torang.di.restaurant_detail_container_di.ProvideRestaurantDetailColumn
import com.sarang.torang.di.settings.provideSettingScreen
import com.sarang.torang.di.splash_di.provideSplashScreen
import com.sarang.torang.di.torangimagepager.provideRestaurantImagePager
import com.sarang.torang.di.torangimagepager.provideReviewImagePager
import com.sarang.torang.dialogsbox.compose.DialogsBoxViewModel
import com.sarang.torang.viewmodel.profile.MyFeedListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProvideTorangScreen() {
    val rootNavController   : RootNavController        = RootNavController(rememberNavController())
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
        restaurantScreen            = ProvideRestaurantDetailColumn(rootNavController) ,
        modReviewScreen             = provideModReviewScreen(rootNavController),
        emailLoginScreen            = provideEmailLoginNavHost(rootNavController),
        imagePagerScreen            = provideReviewImagePager(rootNavController),
        restaurantImagePagerScreen  = provideRestaurantImagePager(rootNavController),
        likesScreen                 = provideLikeScreen(rootNavController),
        feedScreenByReviewId        = provideFeedScreenByReviewId(rootNavController = rootNavController),
        myFeedScreenByReviewId      = { ProvideMyFeedScreen(rootNavController = rootNavController, reviewId = it) },
        mapScreen                   = provideMapScreen(rootNavController),
        alarmScreen                 = provideAlarmScreen(rootNavController)
    )
}