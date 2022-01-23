package org.techtown.plogging_android.RecruitCrew.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.plogging_android.RecruitCrew.CrewInfo.CrewIntroFragment
import org.techtown.plogging_android.RecruitCrew.CrewInfo.MemberFragment
import org.techtown.plogging_android.RecruitCrew.Retrofit.CrewInfoResult
import org.techtown.plogging_android.RecruitCrew.Retrofit.CrewMemberResult

class CrewInfoViewpagerAdapter(fragment : Fragment , val crewinfo : CrewInfoResult , val list :List<CrewMemberResult> ,val crewIdx:Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> CrewIntroFragment(crewinfo,crewIdx)
            else -> MemberFragment(list,crewIdx)
        }
    }
}