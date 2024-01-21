package com.sryang.addreview.di.addreview_di

import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.usecase.SelectRestaurantUseCase
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.Filter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SelectRestaurantUseCaseImpl {
    @Provides
    fun provideSelectRestaurantService(apiRestaurant: ApiRestaurant): SelectRestaurantUseCase {
        return object : SelectRestaurantUseCase {
            override suspend fun invoke(keyword: String): List<SelectRestaurantData> {
                val result = apiRestaurant.getFilterRestaurant(Filter(keyword = keyword))
                return result.map { it.toSelectRestaurantData() }
            }
        }
    }
}
