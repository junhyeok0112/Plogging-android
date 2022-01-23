package org.techtown.plogging_android.Home.Retrofit

import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HomeRetrofitInterface {
    @GET("/homes")
    fun getHome(@Header("X-ACCESS-TOKEN") jwt:String) : Call<HomeGetResult>

    @GET("/plogs/{plogIdx}")
    fun getRecord(@Header("X-ACCESS-TOKEN") jwt:String , @Path("plogIdx") plogIdx:Int ) :Call<RecordResult>


}