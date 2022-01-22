package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.techtown.plogging_android.RecruitCrew.adapter.CrewInfoViewpagerAdapter
import org.techtown.plogging_android.databinding.FragmentCrewinfoBinding

class CrewInfoFragment : Fragment() {

    lateinit var binding : FragmentCrewinfoBinding
    val tabList = arrayOf("소개" , "멤버")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrewinfoBinding.inflate(inflater , container , false)
        setViewPager()
        return binding.root
    }

    fun setViewPager(){
        val crewInfoViewpagerAdapter = CrewInfoViewpagerAdapter(this)
        binding.crewinfoFrameVp.adapter = crewInfoViewpagerAdapter
        binding.crewinfoFrameVp.offscreenPageLimit = 2
        TabLayoutMediator(binding.crewinfoTl , binding.crewinfoFrameVp){
                tab,position->
            tab.text = tabList[position]
        }.attach()

    }


}