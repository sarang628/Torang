package com.sryang.torang.report.test.di.report

import com.sryang.torang.data.report.ReviewDTO
import com.sryang.torang.usecases.report.BlockUserUseCase
import com.sryang.torang.usecases.report.LoadReviewUseCase
import com.sryang.torang.usecases.report.ReportUseCase
import com.sarang.torang.api.ApiReview
import com.sarang.torang.api.handle
import com.sarang.torang.repository.ReportReason
import com.sarang.torang.repository.ReportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class ReportUsecaseModule
{
    @Provides
    fun providesBlockUserUseCase(): BlockUserUseCase
    {
        return object : BlockUserUseCase
        {
            override suspend fun invoke(userId: Int)
            {

            }
        }
    }

    @Provides
    fun providesReportUseCase(reportRepository: ReportRepository): ReportUseCase
    {
        return object : ReportUseCase
        {
            override suspend fun invoke(reviewId: Int, reason: String)
            {
                reportRepository.sendReportReason(
                    reviewId = reviewId, reportReason = ReportReason.ABUSE
                )
            }
        }
    }

    @Provides
    fun providesLoadReviewUseCase(
        apiReview: ApiReview
    ): LoadReviewUseCase
    {
        return object : LoadReviewUseCase
        {
            override suspend fun invoke(id: Int): ReviewDTO
            {
                try
                {
                    val result = apiReview.getReviewsById(id)
                    return ReviewDTO(
                        userId = result.user.userId,
                        reviewId = result.reviewId,
                        profileUrl = result.user.profilePicUrl,
                        userName = result.user.userName
                    )
                } catch (e: HttpException)
                {
                    throw Exception(e.handle())
                }
            }
        }
    }

}