package com.sryang.login.di.login

import com.sarang.torang.usecase.ValidPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidPasswordUseCaseImpl {
    @Provides
    fun providesValidPasswordUseCase(): ValidPasswordUseCase {
        return object : ValidPasswordUseCase {
            override fun invoke(password: String): Boolean {
                return password.length >= 5
            }
        }
    }
}