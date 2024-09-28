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
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.chatroom.ChatScreen
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.chat_di.ChatActivity
import com.sarang.torang.di.feed_di.shimmerBrush
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.provideFeed
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.torang.ProvideMainDialog
import com.sarang.torang.di.torang.provideAddReviewScreen
import com.sarang.torang.di.main_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.torang.VideoPlayerScreen
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
            val state = rememberPullToRefreshState()

            TorangTheme {
//            ThemeProvider.Twitter {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val rootNavController = RootNavController(rememberNavController())
                    var reviewId: Int? by remember { mutableStateOf(null) }
                    val coroutine = rememberCoroutineScope()
                    val dispatcher =
                        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
                    val context = LocalContext.current
                    TorangScreen(
                        rootNavController = rootNavController,
                        mainScreen = provideMainScreen(
                            rootNavController,
                            videoPlayer = { url, isPlaying, onVideoClick ->
                                VideoPlayerScreen(
                                    videoUrl = url,
                                    isPlaying = isPlaying,
                                    onClick = onVideoClick,
                                    onPlay = {})
                            },
                            addReviewScreen = provideAddReviewScreen(rootNavController),
                            chat = {
                                ChatScreen(
                                    onChat = {
                                        startActivity(
                                            Intent(this, ChatActivity::class.java).apply {
                                                putExtra("roomId", it)
                                            }
                                        )
                                    },
                                    onRefresh = {
                                        coroutine.launch {
                                            state.updateState(RefreshIndicatorState.Default)
                                        }
                                    },
                                    onSearch = {},
                                    onClose = { dispatcher?.onBackPressed() },
                                    pullToRefreshLayout = { isRefreshing, onRefresh, contents ->

                                        if (isRefreshing) {
                                            state.updateState(RefreshIndicatorState.Refreshing)
                                        } else {
                                            state.updateState(RefreshIndicatorState.Default)
                                        }

                                        PullToRefreshLayout(
                                            pullRefreshLayoutState = state,
                                            refreshThreshold = 80,
                                            onRefresh = onRefresh
                                        ) {
                                            contents.invoke()
                                        }
                                    },
                                    image = provideTorangAsyncImage()
                                )
                            },
                            onCloseReview = {

                            },
                            onMessage = {
                                Log.d("__MainActivity", "onMessage : $it")
                                coroutine.launch {
                                    val result = chatRepository.getUserOrCreateRoomByUserId(it)
                                    Log.d("__MainActivity", "result : $result")
                                    startActivity(Intent(context, ChatActivity::class.java).apply {
                                        putExtra("roomId", result.chatRoomEntity.roomId)
                                    })
                                }
                            }
                        ),
                        profileScreen = provideProfileScreen(
                            rootNavController,
                            videoPlayer = { url, isPlaying, onVideoClick ->
                                VideoPlayerScreen(
                                    videoUrl = url,
                                    isPlaying = isPlaying,
                                    onClick = onVideoClick,
                                    onPlay = {})
                            }, onMessage = {
                                Log.d("__MainActivity", "onMessage : $it")
                                coroutine.launch {
                                    val result = chatRepository.getUserOrCreateRoomByUserId(it)
                                    Log.d("__MainActivity", "result : $result")
                                    startActivity(Intent(context, ChatActivity::class.java).apply {
                                        putExtra("roomId", result.chatRoomEntity.roomId)
                                    })
                                }
                            }
                        ),
                        settingsScreen = provideSettingScreen(rootNavController),
                        splashScreen = provideSplashScreen(rootNavController),
                        addReviewScreen = provideAddReviewScreen(rootNavController),
                        loginScreen = provideLoginNavHost(rootNavController),
                        editProfileScreen = provideEditProfileScreen(rootNavController),
                        editProfileImageScreen = provideGalleryNavHost(rootNavController),
                        restaurantScreen = provideRestaurantNavScreen(this, rootNavController),
                        modReviewScreen = provideModReviewScreen(rootNavController),
                        emailLoginScreen = provideEmailLoginNavHost(rootNavController),
                        imagePagerScreen = provideReviewImagePager(rootNavController, onComment = {
                            reviewId = it
                        }),
                        restaurantImagePagerScreen = provideRestaurantImagePager(
                            rootNavController,
                            onComment = {
                                reviewId = it
                            }),
                        likesScreen = provideLikeScreen(rootNavController),
                        feedScreen = {
                            val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
                            ProvideMainDialog(
                                dialogsViewModel = dialogsViewModel,
                                rootNavController = rootNavController
                            ) {
                                FeedScreenByReviewId(
                                    reviewId = it,
                                    shimmerBrush = { it -> shimmerBrush(it) },
                                    feed = provideFeed(
                                        onComment = {
                                            Log.d("__MainActivity", "onComment : $it")
                                            dialogsViewModel.onComment(it)
                                        },
                                        onMenu = { dialogsViewModel.onMenu(it) },
                                        onShare = { dialogsViewModel.onShare(it) },
                                        navController = rootNavController.navController,
                                        rootNavController = rootNavController,
                                        videoPlayer = { url, isPlaying, onVideoClick ->
                                            VideoPlayerScreen(
                                                videoUrl = url,
                                                isPlaying = isPlaying,
                                                onClick = onVideoClick,
                                                onPlay = {})
                                        }
                                    ),
                                    pullToRefreshLayout = { isRefreshing, onRefresh, contents ->

                                        if (isRefreshing) {
                                            state.updateState(RefreshIndicatorState.Refreshing)
                                        } else {
                                            state.updateState(RefreshIndicatorState.Default)
                                        }

                                        PullToRefreshLayout(
                                            pullRefreshLayoutState = state,
                                            refreshThreshold = 80,
                                            onRefresh = onRefresh
                                        ) {
                                            contents.invoke()
                                        }
                                    }
                                )
                            }
                        }
                    )

                    reviewId?.let {
                        provideCommentBottomDialogSheet(rootNavController).invoke(it) {
                            reviewId = null
                        }
                    }
                }
            }
        }
    }
}