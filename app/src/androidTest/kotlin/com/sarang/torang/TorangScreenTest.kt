package com.sarang.torang

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.di.main_di.ProvideMainDialog
import com.sarang.torang.di.main_di.provideFeed
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.torang.provideAddReviewScreen
import com.sarang.torang.di.torang.provideCommentBottomDialogSheet
import com.sarang.torang.di.torang.provideEditProfileScreen
import com.sarang.torang.di.torang.provideEmailLoginNavHost
import com.sarang.torang.di.torang.provideGalleryNavHost
import com.sarang.torang.di.torang.provideLikeScreen
import com.sarang.torang.di.torang.provideLoginNavHost
import com.sarang.torang.di.torang.provideModReviewScreen
import com.sarang.torang.di.torang.provideProfileScreen
import com.sarang.torang.di.torang.provideRestaurantImagePager
import com.sarang.torang.di.torang.provideRestaurantNavScreen
import com.sarang.torang.di.torang.provideReviewImagePager
import com.sarang.torang.di.torang.provideSettingScreen
import com.sarang.torang.di.torang.provideSplashScreen
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TorangScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testMainActivityLaunchesSuccessfully() = runTest {
        // 간단한 UI 요소 확인
        composeTestRule.onNodeWithTag("btnLogin").assertExists()
        composeTestRule.onNodeWithTag("btnLogin").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("tfEmail").performTextInput("sarang628@naver.com")
        composeTestRule.onNodeWithTag("tfEmail").performImeAction() // 'next' 키보드 액션 수행
        composeTestRule.onNodeWithTag("tfPassword").performTextInput("Torang!234")
        composeTestRule.onNodeWithTag("tfPassword").performImeAction() // 'done' 키보드 액션 수행
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("btnSignIn").assertExists()
        composeTestRule.onNodeWithTag("btnSignIn").assertHasClickAction()
        composeTestRule.onNodeWithTag("btnSignIn").performClick()
        composeTestRule.waitForIdle()

        // 피드가 나타날 때까지 기다리기
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("feedItem").fetchSemanticsNodes().isNotEmpty()
        }

        // 피드 요소 확인 (예: 피드 항목이 화면에 존재하는지 확인)
//        composeTestRule.onNodeWithTag("feedItem").assertExists()
    }
}