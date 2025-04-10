package com.sarang.torang

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.chatroom.ChatScreen
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.chat_di.ChatActivity
import com.sarang.torang.di.feed_di.provideBottonDetectingLazyColumn
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.main_di.provideFeed
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.torang.ProvideMainDialog
import com.sarang.torang.di.torang.ProvideTheme
import com.sarang.torang.di.torang.ProvideTorangScreen
import com.sarang.torang.di.torang.VideoPlayerScreen
import com.sarang.torang.di.torang.provideAddReviewScreen
import com.sarang.torang.di.torang.provideEditProfileScreen
import com.sarang.torang.di.torang.provideEmailLoginNavHost
import com.sarang.torang.di.torang.provideGalleryNavHost
import com.sarang.torang.di.torang.provideLikeScreen
import com.sarang.torang.di.torang.provideLoginNavHost
import com.sarang.torang.di.torang.provideModReviewScreen
import com.sarang.torang.di.torang.provideProfileScreen
import com.sarang.torang.di.torang.provideRestaurantImagePager
import com.sarang.torang.di.torang.provideRestaurantNavScreen
import com.sarang.torang.di.torang.provideReviewImagePager
import com.sarang.torang.di.torang.provideSettingScreen
import com.sarang.torang.di.torang.provideSplashScreen
import com.sarang.torang.repository.ChatRepository
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var chatRepository: ChatRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProvideTheme {
                ProvideTorangScreen(chatRepository)
            }
        }
    }
}