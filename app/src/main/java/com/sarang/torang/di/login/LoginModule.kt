package com.sryang.login.di.login

import android.content.Context
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.IsLoginFlowUseCase
import com.sarang.torang.usecase.LogoutUseCase
import com.sarang.torang.usecase.SignUpUseCase
import com.sryang.torang_repository.repository.LoginRepository
import com.sryang.torang_repository.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginServiceModule {
    @Singleton
    @Provides
    fun emailLoginService(
        loginRepository: LoginRepository
    ): EmailLoginUseCase {
        return object : EmailLoginUseCase {
            override suspend fun invoke(id: String, email: String) {
                loginRepository.encEmailLogin(id, email)
            }
        }
    }

    @Singleton
    @Provides
    fun provideIsLoginFlowUseCase(
        loginRepository: LoginRepository,
    ): IsLoginFlowUseCase {
        return object : IsLoginFlowUseCase {
            override val isLogin: Flow<Boolean> get() = loginRepository.isLogin

        }
    }

    @Singleton
    @Provides
    fun provideLogoutUseCase(sessionService: SessionService): LogoutUseCase {
        return object : LogoutUseCase {
            override suspend fun invoke() {
                sessionService.removeToken()
            }
        }
    }

    @Singleton
    @Provides
    fun sessionService(@ApplicationContext context: Context): SessionService {
        return SessionService(context)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        loginRepository: LoginRepository
    ): SignUpUseCase {
        return object : SignUpUseCase {
            override suspend fun confirmCode(
                token: String,
                confirmCode: String,
                name: String,
                email: String,
                password: String
            ): Boolean {
                return loginRepository.encConfirmCode(
                    token = token,
                    confirmCode = confirmCode,
                    name = name,
                    email = email,
                    password = password
                )
            }

            override suspend fun checkEmail(email: String, password: String): String {
                return loginRepository.encCheckEmail(email, password)
            }
        }
    }
}