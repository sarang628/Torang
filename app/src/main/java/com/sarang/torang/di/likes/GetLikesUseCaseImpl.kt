package com.sarang.torang.di.likes

import android.util.Log
import com.sarang.library.FollowUseCase
import com.sarang.library.GetLikesUseCase
import com.sarang.library.IsLoginUseCase
import com.sarang.library.Like
import com.sarang.library.LikeUiState
import com.sarang.library.UnFollowUseCase
import com.sarang.torang.BuildConfig
import com.sarang.torang.api.ApiLike
import com.sarang.torang.repository.FollowRepository
import com.sarang.torang.repository.LikeRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import kotlin.collections.map

@Module
@InstallIn(SingletonComponent::class)
class LikesModule {

    @Provides
    fun provideGetLikesUseCase(
        likeRepository: LikeRepository,
        sessionService: SessionService
    ): GetLikesUseCase {
        return object : GetLikesUseCase {
            val tag = "__provideGetLikesUseCase"
            override suspend fun invoke(reviewId: Int): LikeUiState {
                try {

                    val result = likeRepository.getLikeUserFromReview(reviewId)
                    
                    Log.d(
                        tag,
                        "getLikeUserByReviewId(API) reviewId: $reviewId, result: ${result.size}"
                    )
                    return LikeUiState.Success(
                        result.map {
                            Like(
                                url = BuildConfig.PROFILE_IMAGE_SERVER_URL + it.profilePicUrl,
                                name = it.userName,
                                isFollow = it.isFollow == 1,
                                followerId = it.followerId
                            )
                        }
                    )
                } catch (e: HttpException) {
                    Log.e(tag, "${e.response()?.errorBody()?.string()}")
                } catch (e: Exception) {
                    Log.e(tag, "$e")
                }
                return LikeUiState.Error
            }
        }
    }

    @Provides
    fun provideFollowUseCase(followRepository: FollowRepository): FollowUseCase {
        return object : FollowUseCase {
            override suspend fun invoke(userId: Int): Boolean {
                try {
                    followRepository.follow(userId)
                    return true
                } catch (e: Exception) {
                    return false
                }
            }
        }
    }

    @Provides
    fun provideUnFollowUseCase(followRepository: FollowRepository): UnFollowUseCase {
        return object : UnFollowUseCase {
            override suspend fun invoke(userId: Int): Boolean {
                try {
                    followRepository.unFollow(userId)
                    return true
                } catch (e: Exception) {
                    return false
                }
            }
        }
    }

    @Provides
    fun provideIsLoginUseCase(loginRepository: LoginRepository): IsLoginUseCase {
        return object : IsLoginUseCase {
            override fun invoke(): Flow<Boolean> {
                return loginRepository.isLogin
            }
        }
    }
}