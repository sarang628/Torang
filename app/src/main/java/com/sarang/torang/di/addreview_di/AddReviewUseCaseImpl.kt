package com.sryang.addreview.di.addreview_di

import com.sryang.addreview.usecase.AddReviewUseCase
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.repository.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@InstallIn(SingletonComponent::class)
@Module
class AddReviewUseCaseImpl {
    @Provides
    fun provideReviewService(
        reviewRepository: ReviewRepository
    ): AddReviewUseCase {
        return object : AddReviewUseCase {
            override suspend fun invoke(
                contents: String,
                restaurantId: Int,
                rating: Float,
                files: List<String>
            ) {
                reviewRepository.addReview(
                    contents = contents,
                    restaurantId = restaurantId,
                    rating = rating,
                    files = files
                )
            }
        }
    }
}