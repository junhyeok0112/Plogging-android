package org.techtown.plogging_android.RecruitCrew.Retrofit

data class SignUpResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SignUpResult
)