package org.techtown.plogging_android.RecruitCrew.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CrewInfoRetrofitInterface {
    @GET("/crews/{crewIdx}")
    fun getCrewInfo(@Path("crewIdx") crewIdx:Int) : Call<CrewInfoResponse>

    @GET("/crews/{crewIdx}/member")
    fun getCrewMember(@Path("crewIdx") crewIdx:Int) : Call<CrewMemberResponse>
}