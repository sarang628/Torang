package com.sryang.settings.di.settings

import com.sarang.torang.repository.SettingsRepository
import com.sarang.torang.viewmodels.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@InstallIn(SingletonComponent::class)
@Module
class SettingsModule {
    @Provides
    fun provideSettingsUseCase(
        settingRepository: SettingsRepository
    ): SettingsUseCase {
        return object : SettingsUseCase {
            override suspend fun getId(): Flow<String?> {
                return settingRepository.getUsername()
            }

            override suspend fun logout() {
                settingRepository.logout()
            }
        }
    }
}