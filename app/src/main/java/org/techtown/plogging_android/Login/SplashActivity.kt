package org.techtown.plogging_android.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import org.techtown.plogging_android.databinding.ActivityMainBinding


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        //메인 바인딩
        setContentView(activityMainBinding.root)
        //뷰바인딩과 연결


    }
}