package org.techtown.plogging_android.Mycrew.Retrofit

data class MyCrewResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<MyCrewResult>
)