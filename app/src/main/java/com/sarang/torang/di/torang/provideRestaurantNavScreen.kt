package com.sarang.torang.di.torang

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import com.example.screen_map.compose.MapScreenForRestaurant
import com.example.screen_map.data.MarkerData
import com.google.maps.android.compose.rememberCameraPositionState
import com.sarang.torang.Feed
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.component.Feeds
import com.sarang.torang.compose.restaurant.RestaurantNavScreen
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.uistate.FeedsUiState

internal fun provideRestaurantNavScreen(
    rootNavController: RootNavController,
): @Composable (NavBackStackEntry) -> Unit = { navBackStackEntry ->
    val restaurantId = navBackStackEntry.arguments?.getString("restaurantId")
    val context = LocalContext.current
    restaurantId?.let {
        RestaurantNavScreen(
            restaurantId = it.toInt(),
            feeds = { restaurantId, modifier ->
                provideFeedScreenByRestaurantId(rootNavController).invoke(restaurantId)
            },
            onCall = { number ->
                context.startActivity(
                    Intent(Intent.ACTION_DIAL).apply {
                        setData(Uri.parse("tel:$number"))
                    }
                )
            },
            onWeb = { url ->
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                )
            },
            map = { title, lat, lon, foodType ->
                Log.d("__ScreenProvider", "${title}, ${lat}, ${lon}, ${foodType}")
                val markerData = MarkerData(
                    id = 0,
                    lat = lat,
                    lon = lon,
                    title = title,
                    foodType = foodType
                )
                MapScreenForRestaurant(
                    cameraPositionState = rememberCameraPositionState(),
                    selectedMarkerData = markerData,
                    zoom = 17f
                )
            },
            image = provideTorangAsyncImage(),
            onImage = {
                rootNavController.restaurantImagePager(it)
                Log.d("__ScreenProvider", "onImage id:${it}")
            },
            progressTintColor = Color(0xffe6cc00),
            onBack = { rootNavController.popBackStack() },
            onContents = {
                rootNavController.review(it)
            },
            onProfile = {
                rootNavController.profile(it)
            }
        )
    }
}

