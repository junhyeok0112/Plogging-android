package org.techtown.plogging_android.Home.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeRetrofitInterface {
    @GET("/homes")
    fun getHome(@Header("X-ACCESS-TOKEN") jwt:String) : Call<HomeGetResult>

}