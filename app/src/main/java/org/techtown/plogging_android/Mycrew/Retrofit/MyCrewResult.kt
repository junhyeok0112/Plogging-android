package org.techtown.plogging_android.Mycrew.Retrofit

data class MyCrewResult(
    val crewIdx: Int,
    val currentNum: Int,
    val howmany: Int,
    val name: String,
    val region: String,
    val status: String,
    val targetDay: String,
    val userImage: String
)