package org.techtown.plogging_android.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import org.techtown.plogging_android.databinding.ActivityMainBinding
import org.techtown.plogging_android.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        //메인 바인딩
        setContentView(binding.root)
        //뷰바인딩과 연결

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)


    }
}