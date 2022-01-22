package org.techtown.plogging_android.RecruitCrew.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.plogging_android.RecruitCrew.CrewInfo.CrewIntroFragment
import org.techtown.plogging_android.RecruitCrew.CrewInfo.MemberFragment

class CrewInfoViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> CrewIntroFragment()
            else -> MemberFragment()
        }
    }
}