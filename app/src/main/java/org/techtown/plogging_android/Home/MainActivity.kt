package org.techtown.plogging_android.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.techtown.plogging_android.Archive.MyArchiveFragment
import org.techtown.plogging_android.Plogging.PloggingFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.ActivityMainBinding
import org.techtown.plogging_android.util.myCheckCameraPermission
import org.techtown.plogging_android.util.myCheckPermission

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        org.techtown.plogging_android.util.checkPermission(this)
        supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, HomeFragment())
            .commit()
    }

    fun setListener(){


        binding.mainBottomnaviBnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, HomeFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.plogging ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, PloggingFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.crew_group ->{
//                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , MyCrewFragment())
//                        .commit()
//                    return@setOnItemSelectedListener true
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, MyArchiveFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 10){
            val fragment = supportFragmentManager.findFragmentById(R.id.main_container_fl)
            fragment?.onActivityResult(requestCode,resultCode,data)
        }
        Log.d("ActivityResult", "메인에 있는 Result , ${requestCode}")
    }
}