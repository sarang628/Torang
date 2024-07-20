package com.sarang.torang.di.login

import android.util.Patterns
import com.sarang.torang.usecase.VerifyEmailFormatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidEmailUseCaseImpl {
    @Provides
    fun provides(): VerifyEmailFormatUseCase {
        return object : VerifyEmailFormatUseCase {
            override fun invoke(email: String): Boolean {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        }
    }
}