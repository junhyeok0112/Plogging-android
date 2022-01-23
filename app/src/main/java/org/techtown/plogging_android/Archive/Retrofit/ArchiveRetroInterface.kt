package org.techtown.plogging_android.Archive.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ArchiveRetroInterface {
    @GET("/plogs/archive")
    fun getArchive(@Header("X-ACCESS-TOKEN") jwt:String) : Call<ArchiveResponse>
}