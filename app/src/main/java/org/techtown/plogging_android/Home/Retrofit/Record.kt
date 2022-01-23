package org.techtown.plogging_android.Home.Retrofit

data class Record(
    val calorie: String,
    val date: String,
    val distance: String,
    val pictures: List<String>,
    val record: String,
    val time: String,
    val title: String
)