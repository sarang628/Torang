package com.sarang.torang.di.restaurant_detail

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_map.compose.MapScreenForRestaurant
import com.example.screen_map.data.MarkerData
import com.google.maps.android.compose.rememberCameraPositionState
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.restaurant.RestaurantNavScreen
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.FeedImage
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.provideFeed
import com.sarang.torang.di.main_di.provideFeedScreenByRestaurantId
import com.sarang.torang.di.pulltorefresh.providePullToRefreshLayout
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.library.pullrefresh.rememberPullToRefreshState

internal fun provideRestaurantNavScreen(
    rootNavController: RootNavController,
): @Composable (NavBackStackEntry) -> Unit = { navBackStackEntry ->
    val restaurantId = navBackStackEntry.arguments?.getString("restaurantId")
    val pullToRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current
    val dialogsViewModel : FeedDialogsViewModel = hiltViewModel()
    val navController = rememberNavController()
    restaurantId?.let {
        RestaurantNavScreen(
            restaurantId = it.toInt(),
            feeds = { restaurantId, modifier ->
                provideFeedScreenByRestaurantId(rootNavController).invoke(restaurantId)
            },
            onCall = { number ->
                context.startActivity(
                    Intent(Intent.ACTION_DIAL).apply {
                        setData("tel:$number".toUri())
                    }
                )
            },
            onWeb = { url ->
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, url.toUri())
                )
            },
            map = { title, lat, lon, foodType ->
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
            imageLoader = provideTorangAsyncImage(),
            onImage = rootNavController::restaurantImagePager,
            progressTintColor = Color(0xffe6cc00),
            onBack = rootNavController::popBackStack,
            onContents = rootNavController::review,
            onProfile = rootNavController::profile,
            pullToRefreshLayout = providePullToRefreshLayout(pullToRefreshState),
            feed = { provideFeed(dialogsViewModel = dialogsViewModel, rootNavController = rootNavController, navController = navController).invoke(
                Feed(it.reviewId, it.restaurantId, it.userId, it.name, it.restaurantName, it.rating, it.profilePictureUrl, it.likeAmount, it.commentAmount, it.isLike, it.isFavorite, it.contents, "", it.reviewImages.map { FeedImage(it, 300, 300) }), {}, {}, false, {}, 200, true ) }
        )
    }

    NavHost(navController = navController, startDestination = "start"){
        composable("start") {  }
        composable("profile/{id}"){}
    }
}

