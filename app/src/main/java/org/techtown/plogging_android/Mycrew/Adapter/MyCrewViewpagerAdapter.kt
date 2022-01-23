package org.techtown.plogging_android.Mycrew.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.plogging_android.Mycrew.ActiveFragment
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResult

class MyCrewViewpagerAdapter (fragment : Fragment , val active : List<MyCrewResult> , val death: List<MyCrewResult> ) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> ActiveFragment(active)
            else -> ActiveFragment(death)
        }
    }

}