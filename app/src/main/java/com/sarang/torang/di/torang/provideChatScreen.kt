package com.sarang.torang.di.torang

import android.content.Intent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.sarang.torang.compose.chatroom.ChatScreen
import com.sarang.torang.di.chat_di.ChatActivity
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.RefreshIndicatorState
import kotlinx.coroutines.launch

fun provideChatScreen(state: PullToRefreshLayoutState): @Composable () -> Unit = {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    ChatScreen(
        onChat = {
            context.startActivity(
                Intent(
                    context,
                    ChatActivity::class.java
                ).apply {
                    putExtra("roomId", it)
                })
        },
        onRefresh = {
            coroutine.launch {
                state.updateState(RefreshIndicatorState.Default)
            }
        },
        onSearch = {},
        onClose = { dispatcher?.onBackPressed() },
        pullToRefreshLayout = providePullToRefreshLayout(state),
        image = provideTorangAsyncImage()
    )
}