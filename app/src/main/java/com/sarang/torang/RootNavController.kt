package com.sarang.torang

import android.util.Log
import androidx.navigation.NavHostController
import com.sarang.torang.di.util.singleTop

class RootNavController(val navController: NavHostController? = null) {
    val tag = "__RootNavController"
    fun modReview(): (Int) -> Unit =                { navController?.navigate("modReview/${it}") }
    fun imagePager(reviewId: Int, position: Int)    { navController?.navigate("imagePager/${reviewId}/${position}") }
    fun imagePager(pictureId: Int)                  { navController?.navigate("imagePager/pictureId/${pictureId}") }
    fun emailLogin()                                { navController?.navigate("emailLogin") }
    fun restaurant(it: Int)                         { navController?.navigate(RootScreen.restaurants + "/${it}") }
    fun settings()                                  { navController?.navigate("settings") }
    fun popBackStack()                              { navController?.popBackStack() }
    fun main()                                      { navController?.navigate("main") { popUpTo(0) } }
    fun restaurantImagePager(it: Int)               { navController?.navigate("restaurantImagePager/${it}") }
    fun addReview()                                 { navController?.navigate("addReview") }
    fun splash()                                    { navController?.navigate("splash") }
    fun editProfileImage()                          { navController?.navigate("EditProfileImage") }
    fun singleTopLogin()                            { navController?.singleTop("login") }
    fun singleTopMain()                             { navController?.singleTop("main") }
    fun profile(userId: Int)                        { navController?.navigate("profile/${userId}") }
    fun like(reviewId: Int)                         { navController?.navigate("like/${reviewId}") }
    fun review(reviewId: Int)                       { navController?.navigate("review/${reviewId}") }
    fun map(restaurantId : Int)                     { navController?.navigate("map/${restaurantId}") }
    fun goAlarm()                                   { navController?.navigate("alarm") }
    fun myReview(reviewId: Int)                     { navController?.navigate("myReview/${reviewId}") }
}