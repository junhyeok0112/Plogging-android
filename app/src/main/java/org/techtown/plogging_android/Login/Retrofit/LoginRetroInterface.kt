package org.techtown.plogging_android.Login.Retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginRetroInterface {
    @POST("/user")
    fun postUser(@Body user:User): Call<SignUpGet>

    @POST("/login")
    fun login(@Body user:LoginUser):Call<LoginGet>


}