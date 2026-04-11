package com.sarang.torang.di.torangimagepager

import com.sarang.torang.BuildConfig
import com.sarang.torang.data.data.RestaurantImagePageContents
import com.sarang.torang.data.data.ReviewImageEntity
import com.sarang.torang.repository.PicturesRepository
import com.sarang.torang.repository.feed.FeedRepository
import com.sarang.torang.uistate.ImagePagerUiState
import com.sarang.torang.usecase.GetPicturesByRestaurantIdUseCase
import com.sarang.torang.usecase.GetReviewForRestaurantImagePagerUseCase
import com.sarang.torang.usecase.GetReviewForReviewImagePagerUseCase
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

                return repository.getFeedPicture(restaurantId).map {
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