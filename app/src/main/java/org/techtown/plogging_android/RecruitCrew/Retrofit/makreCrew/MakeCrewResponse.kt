package org.techtown.plogging_android.RecruitCrew.Retrofit.makreCrew

data class MakeCrewResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: MakeResult
)