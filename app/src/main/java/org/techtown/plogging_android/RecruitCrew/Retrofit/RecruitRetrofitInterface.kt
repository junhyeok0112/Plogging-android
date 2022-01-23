package org.techtown.plogging_android.RecruitCrew.Retrofit

import org.techtown.plogging_android.RecruitCrew.Retrofit.makreCrew.Crew
import org.techtown.plogging_android.RecruitCrew.Retrofit.makreCrew.MakeCrewResponse
import retrofit2.Call
import retrofit2.http.*

interface RecruitRetrofitInterface {
    @GET("/crews")
    fun getRecruitCrewRegion(@Query("region") region:String):Call<RecruitCrewResponse>

    @GET("/crews")
    fun getRecruitCrewAll():Call<RecruitCrewResponse>

    @POST("/crews/{crewIdx}")
    fun signUpCrew(@Header("X-ACCESS-TOKEN") jwt:String ,@Path("crewIdx") crewIdx: Int) : Call<SignUpResponse>

    @POST("/crews")
    fun makeCrew(@Header("X-ACCESS-TOKEN") jwt:String , @Body crew:Crew):Call<MakeCrewResponse>
}