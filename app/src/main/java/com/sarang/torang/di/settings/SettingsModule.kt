package com.sryang.settings.di.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.torangscreensettings.compose.SettingsScreen
import com.example.torangscreensettings.viewmodels.SettingsUseCase
import com.sarang.torang.repository.SettingsRepository
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