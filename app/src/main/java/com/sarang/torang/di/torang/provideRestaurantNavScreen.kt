package com.sarang.torang.di.torang

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
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
    activity: ComponentActivity,
    rootNavController: RootNavController,
): @Composable (NavBackStackEntry) -> Unit = { navBackStackEntry ->
    val restaurantId = navBackStackEntry.arguments?.getString("restaurantId")
    restaurantId?.let {
        RestaurantNavScreen(restaurantId = it.toInt(),
            feeds = provideFeedScreenByRestaurantId(rootNavController),
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
                    selectedMarkerData = markerData
                )
            },
            image = provideTorangAsyncImage(),
            onImage = {
                Log.d("__ScreenProvider", "onImage id:${it}")
                rootNavController.restaurantImagePager(it)
            }
        )
    }
}

