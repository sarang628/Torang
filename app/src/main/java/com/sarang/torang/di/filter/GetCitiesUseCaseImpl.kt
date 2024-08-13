package com.sryang.findinglinkmodules.di.filter

import com.sarang.torang.api.ApiFilter
import com.sarang.torang.data.remote.response.CityApiModel
import com.sryang.screen_filter.data.City
import com.sryang.screen_filter.usecase.GetCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetCitiesUseCaseImpl {
    @Provides
    fun providesGetCitiesUseCase(apiFilter: ApiFilter): GetCitiesUseCase {
        return object : GetCitiesUseCase {
            override suspend fun invoke(): List<City> {
                return apiFilter.getCities().map { it.toCity() }
            }
        }
    }
}

fun CityApiModel.toCity(): City {
    return City(
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        url = "http://sarang628.iptime.org:89/"+this.url,
        zoom = this.zoom
    )
}