package org.techtown.plogging_android.Mycrew

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyCrewViewpagerAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> ActiveFragment()
            else -> ActiveFragment()
        }
    }

}