package com.sarang.torang.di.torang

import ZoomableTorangAsyncImage
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.BuildConfig
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.RestaurantRepository
import com.sryang.imagepager.ImagePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun ProvideRestaurantImagePager(
    viewModel: RestaurantImagePagerViewModel = hiltViewModel(),
    position: Int,
    reviewId: Int,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = reviewId) {
        viewModel.load(reviewId)
    }

    Log.d("__ImagePager", "ProvideImagePager: $uiState, position : $position")

    if (uiState.list.isEmpty()) {
        return
    }

    ImagePager(
        list = uiState.list,
        date = "",
        likeCount = "",
        name = "",
        contents = "",
        commentCount = "",
        position = position,
        image = {
            ZoomableTorangAsyncImage(
                model = it,
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }
    )
}

data class RestaurantImagePagerUiState(
    val list: List<String> = listOf<String>(),
)

@HiltViewModel
class RestaurantImagePagerViewModel @Inject constructor(
    private val repository: RestaurantRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantImagePagerUiState())
    val uiState: StateFlow<RestaurantImagePagerUiState> = _uiState
    fun load(restaurantId: Int) {
        Log.d("__RestaurantImagePagerViewModel", "load restaurantId: $restaurantId")
        viewModelScope.launch {
        }
    }
}