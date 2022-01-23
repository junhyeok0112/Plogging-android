package org.techtown.plogging_android.RecruitCrew.Retrofit

data class CrewMemberResult(
    val comment: String,
    val isKing: String,
    val memberIdx: Int,
    val name: String,
    val userIdx: Int,
    val userImage: String
)