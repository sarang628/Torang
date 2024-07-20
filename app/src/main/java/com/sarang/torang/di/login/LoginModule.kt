package com.sarang.torang.di.login

import android.content.Context
import com.sarang.torang.data.dao.LoggedInUserDao
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.session.SessionService
import com.sarang.torang.usecase.CheckEmailDuplicateUseCase
import com.sarang.torang.usecase.ConfirmCodeUseCase
import com.sarang.torang.usecase.EmailLoginUseCase
import com.sarang.torang.usecase.IsLoginFlowUseCase
import com.sarang.torang.usecase.LogoutUseCase
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
        loginRepository: LoginRepository,
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
    fun provideLogoutUseCase(
        sessionService: SessionService,
        loggedInUserDao: LoggedInUserDao,
    ): LogoutUseCase {
        return object : LogoutUseCase {
            override suspend fun invoke() {
                sessionService.removeToken()
                loggedInUserDao.clear()
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
        loginRepository: LoginRepository,
    ): ConfirmCodeUseCase {
        return object : ConfirmCodeUseCase {
            override suspend fun confirmCode(
                token: String,
                confirmCode: String,
                name: String,
                email: String,
                password: String,
            ): Boolean {
                return loginRepository.encConfirmCode(
                    token = token,
                    confirmCode = confirmCode,
                    name = name,
                    email = email,
                    password = password
                )
            }
        }
    }

    @Singleton
    @Provides
    fun provideCheckEmailUseCase(
        loginRepository: LoginRepository,
    ): CheckEmailDuplicateUseCase {
        return object : CheckEmailDuplicateUseCase {
            override suspend fun checkEmail(email: String, password: String): String {
                return loginRepository.encCheckEmail(email, password)
            }
        }
    }
}