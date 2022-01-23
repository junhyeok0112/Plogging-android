package org.techtown.plogging_android.RecruitCrew.Retrofit

data class CrewInfoResult(
    val crewIdx: Int,
    val description: String,
    val name: String,
    val region: String,
    val status: String,
    val targetDay: String,
    val userImage: String
)