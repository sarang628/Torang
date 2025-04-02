package com.sryang.findinglinkmodules.di.filter

import com.sarang.torang.api.ApiFilter
import com.sryang.screen_filter.data.City
import com.sryang.screen_filter.usecase.GetCitiesByNationIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetCitiesByNationIdUseCaseImpl {
    @Provides
    fun providesGetCitiesUseCase(apiFilter: ApiFilter): GetCitiesByNationIdUseCase {
        return object : GetCitiesByNationIdUseCase {
            override suspend fun invoke(nationId: Int): List<City> {
                return apiFilter.getCitiesByNationId(nationId).map { it.toCity() }
            }
        }
    }
}