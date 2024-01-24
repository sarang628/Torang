package com.example.myapplication.di.restaurant_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.sarang.torang.compose.feed.Feeds
import com.sarang.torang.compose.restaurant.RestaurantNavScreen
import com.sarang.torang.uistate.FeedsUiState

@Composable
fun ProvideRestaurantScreen(backStackEntry: NavBackStackEntry) {
    val restaurantId = backStackEntry.arguments?.getString("restaurantId")
    restaurantId?.let {
        RestaurantNavScreen(restaurantId = it.toInt(),
            feeds = {
                Box {
                    Feeds(
                        feedsUiState = FeedsUiState.Success(arrayListOf()),
                        isRefreshing = false,
                        onRefresh = { },
                        onBottom = {},
                    )
                }
            })
    }
}