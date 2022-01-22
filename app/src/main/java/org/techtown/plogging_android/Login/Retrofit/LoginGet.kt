package org.techtown.plogging_android.Login.Retrofit

data class LoginGet(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: LoginResult
)

data class LoginResult(
    val jwt: String,
    val userIdx: Int
)