package org.techtown.plogging_android.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.ActivityMyRecordBinding

class MyRecordActivity : AppCompatActivity() {

    lateinit var binding :ActivityMyRecordBinding
    var idx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        idx = intent.getIntExtra("idx",0)
        Log.d("idx" , "${idx}")

    }
}