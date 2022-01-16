package org.techtown.plogging_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techtown.plogging_android.Mycrew.MyCrewFragment
import org.techtown.plogging_android.Plogging.CompletePloggingFragment
import org.techtown.plogging_android.Plogging.PloggingFragment
import org.techtown.plogging_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , HomeFragment())
            .commit()
    }

    fun setListener(){
        binding.mainBottomnaviBnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , HomeFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.plogging->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , PloggingFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.crew_group ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , MyCrewFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.profile->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_container_fl , MyArchiveFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}