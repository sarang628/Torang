package com.sarang.torang.di.torang

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.sarang.torang.RootNavController
import com.sarang.torang.di.chat_di.ChatActivity
import com.sarang.torang.repository.ChatRepository
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun provideMainScreen(
    rootNavController: RootNavController,
    state: PullToRefreshLayoutState,
    chatRepository: ChatRepository
): @Composable () -> Unit {
    return {
        val context = LocalContext.current
        val coroutine = rememberCoroutineScope()
        com.sarang.torang.di.main_di.provideMainScreen(
            rootNavController,
            videoPlayer = provideVideoPlayer(),
            addReviewScreen = provideAddReviewScreen(rootNavController),
            chat = provideChatScreen(state),
            onMessage = {
                coroutine.launch {
                    val result = chatRepository.getUserOrCreateRoomByUserId(it)
                    context.startActivity(Intent(context, ChatActivity::class.java).apply {
                        putExtra("roomId", result.chatRoomEntity.roomId)
                    })
                }
            }).invoke()
    }
}