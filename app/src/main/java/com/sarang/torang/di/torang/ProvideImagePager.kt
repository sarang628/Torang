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
fun ProvideImagePager(
    viewModel: ImagePagerViewModel = hiltViewModel(),
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
        date = uiState.date,
        likeCount = uiState.likeCount,
        name = uiState.name,
        contents = uiState.contents,
        commentCount = uiState.commentCount,
        position = position,
        image = {
            ZoomableTorangAsyncImage(
                model = it,
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }
    )
}

data class ImagePagerUiState(
    val list: List<String> = listOf<String>(),
    val date: String = "",
    val likeCount: String = "",
    val name: String = "",
    val contents: String = "",
    val commentCount: String = "",
)

@HiltViewModel
class ImagePagerViewModel @Inject constructor(
    private val repository: FeedRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ImagePagerUiState())
    val uiState: StateFlow<ImagePagerUiState> = _uiState

    fun load(reviewId: Int) {
        Log.d("__ImagePagerViewModel", "load: $reviewId")
        viewModelScope.launch {
            repository.feeds.flatMapMerge { repository.getMyFeed(reviewId) }.map { it.find { it.review.reviewId == reviewId } }.map {
                ImagePagerUiState(
                    list = it?.images?.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl }
                        ?: listOf(),
                    name = it?.review?.userName.toString(),
                    date = it?.review?.createDate.toString(),
                    likeCount = it?.review?.likeAmount.toString(),
                    contents = it?.review?.contents.toString(),
                    commentCount = "${it?.review?.commentAmount.toString()} comments",
                )
            }.collect { uiState ->
                _uiState.update { uiState }
            }
        }
    }
}