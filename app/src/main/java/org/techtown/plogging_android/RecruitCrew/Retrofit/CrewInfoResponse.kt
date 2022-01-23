package org.techtown.plogging_android.RecruitCrew.Retrofit

data class CrewInfoResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: CrewInfoResult
)