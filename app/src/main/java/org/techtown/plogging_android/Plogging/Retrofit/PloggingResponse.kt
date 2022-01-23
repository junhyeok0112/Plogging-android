package org.techtown.plogging_android.Plogging.Retrofit

data class PloggingResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String
)