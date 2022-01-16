package org.techtown.plogging_android.Mycrew

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentMyCrewBinding

class MyCrewFragment : Fragment() {

    lateinit var binding : FragmentMyCrewBinding
    val tabList = arrayListOf("활동중" ,"종료됨")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCrewBinding.inflate(inflater , container , false)
        setViewpager()
        return binding.root
    }

    fun setViewpager(){
        val myCrewViewpagerAdapter = MyCrewViewpagerAdapter(this)
        binding.myCrewViewpagerV2.adapter = myCrewViewpagerAdapter
        binding.myCrewViewpagerV2.offscreenPageLimit = 2
        TabLayoutMediator(binding.myCrewTabTl , binding.myCrewViewpagerV2){
                tab,position->
            tab.text = tabList[position]
        }.attach()

    }

}