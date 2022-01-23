package org.techtown.plogging_android.Home.Retrofit

import android.icu.text.AlphabeticIndex

data class RecordResult(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Record
)