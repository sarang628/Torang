package com.sarang.torang.di.login

import com.sarang.torang.data.LoginErrorMessage
import com.sarang.torang.usecase.VerifyPasswordFormatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidPasswordUseCaseImpl {
    @Provides
    fun providesValidPasswordUseCase(): VerifyPasswordFormatUseCase {
        return object : VerifyPasswordFormatUseCase {
            override fun invoke(password: String): LoginErrorMessage? {
                if (password.length < 6) return LoginErrorMessage.InvalidPassword
                val hasUpperCase = password.any { it.isUpperCase() }
                val hasLowerCase = password.any { it.isLowerCase() }
                val hasDigit = password.any { it.isDigit() }
                val hasSpecialChar = password.any { "!@#\$%^&*()_+[]{}|;':,.<>?".contains(it) }

                return if (hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar)
                    null
                else
                    LoginErrorMessage.InvalidPassword
            }
        }
    }
}