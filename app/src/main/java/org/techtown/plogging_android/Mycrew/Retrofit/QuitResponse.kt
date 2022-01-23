package org.techtown.plogging_android.Mycrew.Retrofit

data class QuitResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)