package org.techtown.plogging_android

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


//앱 전역에서 파이어베이스 인증 객체를 이용하고자 Application을 상속받은 클래스
class MyApplication : MultiDexApplication(){        //MultiDexApplication 은 함수 개수의 제한을 풀어줌
    companion object{
        var jwt : String? = null
        var id : String? = null
        //전역으로 db와 storage에 접근 할 수 있게 선언
        lateinit var db : FirebaseFirestore
        lateinit var storage:FirebaseStorage
    }

    override fun onCreate() {
        super.onCreate()

        //초기화
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}