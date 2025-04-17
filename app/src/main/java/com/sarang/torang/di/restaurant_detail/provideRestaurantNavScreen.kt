package com.sarang.torang.di.restaurant_detail

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavBackStackEntry
import com.example.screen_map.compose.MapScreenForRestaurant
import com.example.screen_map.data.MarkerData
import com.google.maps.android.compose.rememberCameraPositionState
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.restaurant.RestaurantNavScreen
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.main_di.provideFeedScreenByRestaurantId

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
            image = provideTorangAsyncImage(),
            onImage = rootNavController::restaurantImagePager,
            progressTintColor = Color(0xffe6cc00),
            onBack = rootNavController::popBackStack,
            onContents = rootNavController::review,
            onProfile = rootNavController::profile
        )
    }
}

