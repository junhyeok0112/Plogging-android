package org.techtown.plogging_android.RecruitCrew.Retrofit

data class RecruitResult(
    val crewIdx: Int,
    val currentNum: Int,
    val howmany: Int,
    val name: String,
    val region: String,
    val status: String,
    val targetDay: String,
    val userImage: String
)