package org.techtown.plogging_android.Home.Retrofit

data class HomeGetResult(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)