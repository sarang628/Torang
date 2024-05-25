package com.sarang.torang.di.torangimagepager

import com.sarang.torang.BuildConfig
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.PicturesRepository
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
                val it = repository.getFeedByReviewId(reviewId)
                return ImagePagerUiState(
                    list = it.images.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.pictureUrl },
                    name = it.review.userName,
                    date = it.review.createDate,
                    likeCount = it.review.likeAmount.toString(),
                    contents = it.review.contents,
                    commentCount = "${it.review.commentAmount} comments",
                )
            }
        }
    }

    @Provides
    fun provideGetReviewForRestaurantImagePagerUseCase(repository: FeedRepository): GetReviewForRestaurantImagePagerUseCase {
        return object : GetReviewForRestaurantImagePagerUseCase {
            override suspend fun invoke(reviewId: Int): RestaurantImagePageContents {
                val it = repository.getFeedByReviewId(reviewId)
                return RestaurantImagePageContents(
                    contents = it.review.contents,
                    likeCount = it.review.likeAmount.toString(),
                    name = it.review.userName,
                    commentCount = it.review.commentAmount.toString()
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
                        restaurantId = it.restaurantId,
                        reviewId = it.reviewId,
                        userId = it.userId,
                        createDate = it.createDate,
                        menu = it.menu,
                        menuId = it.menuId
                    )
                }
            }
        }
    }
}