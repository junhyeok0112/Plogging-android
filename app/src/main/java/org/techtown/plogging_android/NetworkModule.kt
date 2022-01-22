package org.techtown.plogging_android

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetorfit() : Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://byeongwoo.shop:8080/")          //retrofit 객체 생성
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}