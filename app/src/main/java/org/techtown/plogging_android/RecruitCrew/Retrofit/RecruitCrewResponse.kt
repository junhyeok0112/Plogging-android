package org.techtown.plogging_android.RecruitCrew.Retrofit

data class RecruitCrewResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<RecruitResult>
)