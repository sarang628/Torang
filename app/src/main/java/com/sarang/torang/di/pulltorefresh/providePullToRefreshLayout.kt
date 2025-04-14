package com.sarang.torang.di.pulltorefresh

import androidx.compose.runtime.Composable
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.RefreshIndicatorState

fun providePullToRefreshLayout(state : PullToRefreshLayoutState): @Composable (Boolean, () -> Unit, @Composable () -> Unit) -> Unit {
    return { isRefreshing, onRefresh, contents ->

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
}