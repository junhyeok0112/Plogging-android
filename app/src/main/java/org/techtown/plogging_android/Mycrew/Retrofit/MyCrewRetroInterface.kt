package org.techtown.plogging_android.Mycrew.Retrofit

import retrofit2.Call
import retrofit2.http.*

interface MyCrewRetroInterface {
    @GET("/crews/mycrews")
    fun getMyCrews(@Header("X-ACCESS-TOKEN") jwt:String ,@Query("status") status:String) : Call<MyCrewResponse>

    @DELETE("/crews/{crewIdx}")
    fun deleteCrew(@Header("X-ACCESS-TOKEN") jwt:String ,@Path("crewIdx") crewIdx:Int) : Call<QuitResponse>
}