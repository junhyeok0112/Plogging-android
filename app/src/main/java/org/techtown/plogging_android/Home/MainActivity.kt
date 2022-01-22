package org.techtown.plogging_android.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
            .addToBackStack(null).commit()
    }

    fun setListener(){

        binding.mainBottomnaviBnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, HomeFragment(),"home")
                        .addToBackStack(null).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.plogging ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, PloggingFragment(),"plogging")
                        .addToBackStack(null).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.crew_group ->{
//                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , MyCrewFragment(),"mycrew")
//                        .commit()
//                    return@setOnItemSelectedListener true
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl, MyArchiveFragment(),"myarchive")
                        .addToBackStack(null).commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    fun updateBottomMenu(navigation: BottomNavigationView) {
        val tag1: Fragment? = supportFragmentManager.findFragmentByTag("home")
        val tag2: Fragment? = supportFragmentManager.findFragmentByTag("plogging")
        val tag3: Fragment? = supportFragmentManager.findFragmentByTag("mycrew")
        val tag4: Fragment? = supportFragmentManager.findFragmentByTag("myarchive")
        Log.d("navi", "실행")

        if(tag1 != null && tag1.isVisible()) navigation.menu.findItem(R.id.home).setChecked(true)
        else if(tag2 != null && tag2.isVisible()) navigation.menu.findItem(R.id.plogging).setChecked(true)
        else if(tag3 != null && tag3.isVisible()) navigation.menu.findItem(R.id.crew_group).setChecked(true)
        else if(tag4 != null && tag4.isVisible()) navigation.menu.findItem(R.id.profile).setChecked(true)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateBottomMenu(binding.mainBottomnaviBnv)
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