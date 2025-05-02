package com.sarang.torang.di.restaurant_detail

import com.sarang.torang.api.ApiRestaurant
import com.sarang.torang.api.ApiReview
import com.sarang.torang.api.handle
import com.sarang.torang.data.RestaurantDetail
import com.sarang.torang.data.restaurant.Feed
import com.sarang.torang.data.restaurant.MenuData
import com.sarang.torang.data.restaurant.RestaurantImage
import com.sarang.torang.data.restaurant.RestaurantInfoData
import com.sarang.torang.repository.RestaurantRepository
import com.sarang.torang.uistate.RestaurantInfoUIState
import com.sarang.torang.usecase.FetchRestaurantUseCase
import com.sarang.torang.usecase.FetchReviewsUseCase
import com.sarang.torang.usecase.GetMenuUseCase
import com.sarang.torang.usecase.GetRestaurantGalleryUseCase
import com.sarang.torang.usecase.GetRestaurantInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {
    @Provides
    fun providesFetchReviewsUseCase(apiReview: ApiReview): FetchReviewsUseCase {
        return object : FetchReviewsUseCase {
            override suspend fun invoke(restaurantId: Int): List<Feed> {
                try {
                    return apiReview.getReviews(restaurantId).map { it.toFeedData() }
                } catch (e: HttpException) {
                    throw Exception(e.handle())
                }
            }
        }
    }

    @Provides
    fun providesFetchRestaurantUseCase(
        restaurantRepository: RestaurantRepository,
    ): FetchRestaurantUseCase {
        return object : FetchRestaurantUseCase {
            override suspend fun invoke(restaurantId: Int): RestaurantInfoData {
                try {
                    val result: RestaurantDetail =
                        restaurantRepository.loadRestaurantDetail(restaurantId)
                    return result.toRestaurantInfoData()

                } catch (e: HttpException) {
                    val message = e.handle()
                    throw Exception(message)
                } catch (e: Exception) {
                    throw Exception(e.message)
                }
            }
        }
    }

    @Provides
    fun providesGetRestaurantGalleryUseCase(
        apiRestaurant: ApiRestaurant,
    ): GetRestaurantGalleryUseCase {
        return object : GetRestaurantGalleryUseCase {
            override suspend fun invoke(restaurantId: Int): List<RestaurantImage> {
                return apiRestaurant.getRestaurantDetail(restaurantId).toRestaurantImages()
            }
        }
    }

    @Provides
    fun providesGetRestaurantInfoUseCase(apiRestaurant: ApiRestaurant): GetRestaurantInfoUseCase {
        return object : GetRestaurantInfoUseCase {
            override suspend fun invoke(restaurantId: Int): RestaurantInfoData {
                return apiRestaurant.getRestaurantDetail(restaurantId).toRestaurantInfoData()
            }
        }
    }

    @Provides
    fun providesGetMenuUseCase(apiRestaurant: ApiRestaurant): GetMenuUseCase {
        return object : GetMenuUseCase {
            override suspend fun invoke(restaurantId: Int): List<MenuData> {
                return apiRestaurant.getRestaurantDetail(restaurantId).toMenus()
            }
        }
    }
}