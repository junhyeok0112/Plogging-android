package org.techtown.plogging_android.Home.Retrofit

data class Result(
    val calendar: List<Calendar>,
    val distanceSum: String,
    val name: String,
    val plogSum: String,
    val timeSum: String,
    val userImage: String
)