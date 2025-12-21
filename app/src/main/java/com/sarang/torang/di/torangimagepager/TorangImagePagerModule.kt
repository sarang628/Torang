package com.sarang.torang.di.torangimagepager

import com.sarang.torang.BuildConfig
import com.sarang.torang.repository.PicturesRepository
import com.sarang.torang.repository.feed.FeedRepository
import com.sryang.library.data.RestaurantImagePageContents
import com.sryang.library.data.ReviewImageEntity
import com.sryang.library.uistate.ImagePagerUiState
import com.sryang.library.usecase.GetPicturesByRestaurantIdUseCase
import com.sryang.library.usecase.GetReviewForRestaurantImagePagerUseCase
import com.sryang.library.usecase.GetReviewForReviewImagePagerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TorangImagePagerModule {
    @Provides
    fun provideGetReviewUseCase(repository: FeedRepository): GetReviewForReviewImagePagerUseCase {
        return object : GetReviewForReviewImagePagerUseCase {
            override suspend fun invoke(reviewId: Int): ImagePagerUiState {
                val it = repository.findById(reviewId)
                return ImagePagerUiState(
                    list = it.images.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl },
                    name = it.review.userName ?: "",
                    date = it.review.createDate ?: "",
                    likeCount = it.review.likeAmount.toString(),
                    contents = it.review.contents ?: "",
                    commentCount = "${it.review.commentAmount} comments",
                    userId = it.review.userId ?: 0,
                    reviewId = it.review.reviewId
                )
            }
        }
    }

    @Provides
    fun provideGetReviewForRestaurantImagePagerUseCase(repository: FeedRepository): GetReviewForRestaurantImagePagerUseCase {
        return object : GetReviewForRestaurantImagePagerUseCase {
            override suspend fun invoke(reviewId: Int): RestaurantImagePageContents {
                val it = repository.findById(reviewId)
                return RestaurantImagePageContents(
                    contents = it.review.contents ?: "",
                    likeCount = it.review.likeAmount.toString(),
                    name = it.review.userName ?: "",
                    commentCount = it.review.commentAmount.toString(),
                    reviewId = it.review.reviewId,
                    userId = it.review.userId ?: 0
                )
            }
        }
    }

    @Provides
    fun provideGetPicturesByRestaurantIdUseCase(repository: PicturesRepository): GetPicturesByRestaurantIdUseCase {
        return object : GetPicturesByRestaurantIdUseCase {
            override suspend fun invoke(restaurantId: Int): List<ReviewImageEntity> {

                return repository.getImagesByImageId(restaurantId).map {
                    ReviewImageEntity(
                        pictureId = it.pictureId,
                        pictureUrl = BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl,
                        restaurantId = it.restaurantId ?: 0,
                        reviewId = it.reviewId ?: 0,
                        userId = it.userId ?: 0,
                        createDate = it.createDate ?: "",
                        menu = it.menu ?: 0,
                        menuId = it.menuId ?: 0
                    )
                }
            }
        }
    }
}