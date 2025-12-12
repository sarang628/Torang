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
    tag                         : String                                    = "__TorangScreen",
    loginScreen                 : @Composable () -> Unit                    = {},
    settingsScreen              : @Composable () -> Unit                    = {},
    splashScreen                : @Composable () -> Unit                    = {},
    editProfileScreen           : @Composable () -> Unit                    = {},
    editProfileImageScreen      : @Composable () -> Unit                    = {},
    mainScreen                  : @Composable () -> Unit                    = {},
    emailLoginScreen            : @Composable () -> Unit                    = {},
    alarmScreen                 : @Composable () -> Unit                    = {},
    mapScreen                   : @Composable (Int?) -> Unit                = {},
    restaurantImagePagerScreen  : @Composable (Int) -> Unit                 = {},
    likesScreen                 : @Composable (Int) -> Unit                 = {},
    feedScreenByReviewId        : @Composable (Int) -> Unit                 = {},
    myFeedScreenByReviewId      : @Composable (Int) -> Unit                 = {},
    imagePagerScreen            : @Composable (Int, Int) -> Unit            = {_,_->},
    profileScreen               : @Composable (NavBackStackEntry) -> Unit   = {},
    modReviewScreen             : @Composable (NavBackStackEntry) -> Unit   = {},
    restaurantScreen            : @Composable (Int) -> Unit                 = {},
) {
    val tag = "__TorangScreen"
    rootNavController.navController?.let {
        NavHost(navController = it, startDestination = "main",) {
            composable("main") { mainScreen.invoke() }
            composable("${RootScreen.restaurants}/{restaurantId}") { backStackEntry ->
                backStackEntry.arguments?.getString("restaurantId")?.toInt()?.let {
                    restaurantScreen.invoke(it)
                } ?: run {
                    Log.e(tag, "restaurantId argument error ${backStackEntry.arguments?.getString("restaurantId")}")
                }
            }
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
                else { Log.e(tag, "reviewId is null") }
            }
            composable("restaurangImagePager/{imageId}") {
                val imageId = it.arguments?.getString("imageId")?.toInt()
                Log.d("__TorangScreen", "navigate ImagePager : imageId : $imageId")
                if (imageId != null) restaurantImagePagerScreen.invoke(imageId)
                else { Log.e(tag, "imageId is null") }
            }
            composable("like/{reviewId}") {
                val reviewId = it.arguments?.getString("reviewId")?.toInt()
                if (reviewId == null) { Log.e(tag, "reviewId is null in likeScreen") }
                else { likesScreen.invoke(reviewId) }
            }
            composable("review/{reviewId}") {
                val reviewId = it.arguments?.getString("reviewId")?.toInt()
                if (reviewId == null) { Log.e(tag, "reviewId is null in feedScreen") }
                else { feedScreenByReviewId.invoke(reviewId) }
            }
            composable("map/{restaurantId}"){
                val restaurantId = it.arguments?.getString("restaurantId")?.toInt()
                mapScreen.invoke(restaurantId)
            }
            composable("alarm"){
                alarmScreen.invoke()
            }
            composable("myReview/{reviewId}") {
                val reviewId = it.arguments?.getString("reviewId")?.toInt()
                if (reviewId == null) { Log.e(tag, "reviewId is null in feedScreen") }
                else { myFeedScreenByReviewId.invoke(reviewId) }
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
    fun restaurant(it: Int)                         {
        Log.d(tag, "restaurant: $it")
        navController?.navigate(RootScreen.restaurants + "/${it}")
    }
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
    fun goAlarm()                                   { navController?.navigate("alarm") }
    fun myReview(reviewId: Int)                     { Log.d(tag, "review. reviewId:${reviewId}"); navController?.navigate("myReview/${reviewId}") }
}