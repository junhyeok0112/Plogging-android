package org.techtown.plogging_android.RecruitCrew.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.agatha_plog.RecruitCrewFragment


//모집 메인화면 어댑터
class RecruitViewpagerAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 8

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> RecruitCrewFragment()
            1-> RecruitCrewFragment()
            2-> RecruitCrewFragment()
            3-> RecruitCrewFragment()
            4-> RecruitCrewFragment()
            5-> RecruitCrewFragment()
            6-> RecruitCrewFragment()
            else-> RecruitCrewFragment()
        }
    }
}