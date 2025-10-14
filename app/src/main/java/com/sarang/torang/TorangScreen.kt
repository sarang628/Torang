package com.sarang.torang

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarang.torang.di.util.singleTop

/**
 * @param profileScreen 피드화면에서 프로필 클릭 시 이동하는 화면. 메인 하단 내 프로필과 관련없음.
 */
@Composable
fun TorangScreen(
    rootNavController           : RootNavController,
    loginScreen                 : @Composable () -> Unit                    = {},
    settingsScreen              : @Composable () -> Unit                    = {},
    splashScreen                : @Composable () -> Unit                    = {},
    editProfileScreen           : @Composable () -> Unit                    = {},
    editProfileImageScreen      : @Composable () -> Unit                    = {},
    mainScreen                  : @Composable () -> Unit                    = {},
    emailLoginScreen            : @Composable () -> Unit                    = {},
    mapScreen                   : @Composable (Int?) -> Unit                = {},
    restaurantImagePagerScreen  : @Composable (Int) -> Unit                 = {},
    likesScreen                 : @Composable (Int) -> Unit                 = {},
    feedScreenByReviewId        : @Composable (Int) -> Unit                 = {},
    imagePagerScreen            : @Composable (Int, Int) -> Unit            = {_,_->},
    addReviewScreen             : @Composable (onClose: () -> Unit) -> Unit = {},
    profileScreen               : @Composable (NavBackStackEntry) -> Unit   = {},
    modReviewScreen             : @Composable (NavBackStackEntry) -> Unit   = {},
    restaurantScreen            : @Composable (NavBackStackEntry) -> Unit   = {},
) {
    rootNavController.navController?.let {
        NavHost(navController = it, startDestination = "main",) {
            composable("main") { mainScreen.invoke() }
            composable("addReview") { addReviewScreen.invoke({}) }
            composable("${RootScreen.restaurants}/{restaurantId}") { backStackEntry -> restaurantScreen.invoke(backStackEntry) }
            composable("profile/{id}") { profileScreen.invoke(it) }
            composable("splash") { splashScreen.invoke() }
            composable("login") { loginScreen.invoke() }
            composable("settings") { settingsScreen.invoke() }
            composable("editProfile") { editProfileScreen.invoke() }
            composable("EditProfileImage") { editProfileImageScreen.invoke() }
            composable("modReview/{reviewId}") { modReviewScreen.invoke(it) }
            composable("emailLogin") { emailLoginScreen.invoke() }
            composable("imagePager/{reviewId}/{position}") {
                val reviewId = it.arguments?.getString("reviewId")?.toInt()
                val position = it.arguments?.getString("position")?.toInt()
                Log.d("__TorangScreen", "navigate ImagePager : reviewId : $reviewId, position : $position")
                if (reviewId != null) imagePagerScreen.invoke(reviewId, position ?: 0)
                else { Log.e("__TorangScreen", "reviewId is null") }
            }
            composable("restaurangImagePager/{imageId}") {
                val imageId = it.arguments?.getString("imageId")?.toInt()
                Log.d("__TorangScreen", "navigate ImagePager : imageId : $imageId")
                if (imageId != null) restaurantImagePagerScreen.invoke(imageId)
                else { Log.e("__TorangScreen", "imageId is null") }
            }
            composable("like/{reviewId}") {
                val reviewId = it.arguments?.getString("reviewId")?.toInt()
                if (reviewId == null) { Log.e("__TorangScreen", "reviewId is null in likeScreen") }
                else { likesScreen.invoke(reviewId) }
            }
            composable("review/{reviewId}") {
                val reviewId = it.arguments?.getString("reviewId")?.toInt()
                if (reviewId == null) { Log.e("__TorangScreen", "reviewId is null in feedScreen") }
                else { feedScreenByReviewId.invoke(reviewId) }
            }
            composable("map/{restaurantId}"){
                val restaurantId = it.arguments?.getString("restaurantId")?.toInt()
                mapScreen.invoke(restaurantId)
            }
        }
    }
}


object RootScreen {
    object Main
    object AddReview

    val restaurants: String = "restaurants"
}

class RootNavController(val navController: NavHostController? = null) {
    val tag = "__RootNavController"
    fun modReview(): (Int) -> Unit =                { navController?.navigate("modReview/${it}") }
    fun imagePager(reviewId: Int, position: Int)    { navController?.navigate("imagePager/${reviewId}/${position}") }
    fun emailLogin()                                { navController?.navigate("emailLogin") }
    fun restaurant(it: Int)                         { navController?.navigate(RootScreen.restaurants + "/${it}") }
    fun settings()                                  { navController?.navigate("settings") }
    fun popBackStack()                              { navController?.popBackStack() }
    fun main()                                      { navController?.navigate("main") { popUpTo(0) } }
    fun restaurantImagePager(it: Int)               { navController?.navigate("restaurangImagePager/${it}") }
    fun addReview()                                 { navController?.navigate("addReview") }
    fun splash()                                    { navController?.navigate("splash") }
    fun editProfileImage()                          { navController?.navigate("EditProfileImage") }
    fun singleTopLogin()                            { navController?.singleTop("login") }
    fun singleTopMain()                             { navController?.singleTop("main") }
    fun profile(userId: Int)                        { Log.d(tag, "profile. userId:$userId") ; navController?.navigate("profile/${userId}") }
    fun like(reviewId: Int)                         { navController?.navigate("like/${reviewId}") }
    fun review(reviewId: Int)                       { Log.d(tag, "review. reviewId:${reviewId}"); navController?.navigate("review/${reviewId}") }
    fun map(restaurantId : Int)                     { Log.d(tag, "goMap"); navController?.navigate("map/${restaurantId}") }
}