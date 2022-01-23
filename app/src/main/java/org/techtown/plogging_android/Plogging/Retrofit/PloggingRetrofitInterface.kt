package org.techtown.plogging_android.Plogging.Retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PloggingRetrofitInterface {
    @POST("/plogs")
    fun savePlog(@Header("X-ACCESS-TOKEN") jwt:String ,@Body plog:PloggingData) : Call<PloggingResponse>
}