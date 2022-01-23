package org.techtown.plogging_android.RecruitCrew.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.agatha_plog.RecruitCrewFragment
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitResult


//모집 메인화면 어댑터
class RecruitViewpagerAdapter(fragment:Fragment ,val list: ArrayList<List<RecruitResult>>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int  = 9

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> RecruitCrewFragment(list[position])
            1-> RecruitCrewFragment(list[position])
            2-> RecruitCrewFragment(list[position])
            3-> RecruitCrewFragment(list[position])
            4-> RecruitCrewFragment(list[position])
            5-> RecruitCrewFragment(list[position])
            6-> RecruitCrewFragment(list[position])
            7-> RecruitCrewFragment(list[position])
            else->RecruitCrewFragment(list[position])
        }
    }
}