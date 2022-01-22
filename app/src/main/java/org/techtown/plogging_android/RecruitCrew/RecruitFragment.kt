package com.example.agatha_plog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.techtown.plogging_android.RecruitCrew.adapter.RecruitViewpagerAdapter
import org.techtown.plogging_android.databinding.FragmentRecruitBinding


//모집 메인 화면
class RecruitFragment : Fragment() {

    lateinit var binding: FragmentRecruitBinding
    val tabList = arrayOf("전체","서울","인천","대전","대구","부산","울산","광주","제주도")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitBinding.inflate(inflater , container ,false)
        setViewpager()
        return binding.root
    }

    fun setViewpager(){
        val recruitViewpagerAdapter = RecruitViewpagerAdapter(this)
        binding.recruitVp.adapter = recruitViewpagerAdapter
        binding.recruitVp.offscreenPageLimit = 9
        TabLayoutMediator(binding.recruitTabLayout , binding.recruitVp){
                tab,position->
            tab.text = tabList[position]
        }.attach()
    }
}