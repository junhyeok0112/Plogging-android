package org.techtown.plogging_android.RecruitCrew.Retrofit.makreCrew

data class Crew (
    var contact:String,
    var createAt:String,
    var description:String,
    var howmany:Int,
    var name:String,
    var region:String,
    var targetDay:String,
    var userIdx:Int =0
)