package org.techtown.plogging_android.Home.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.plogging_android.Home.RecordImageFragment

class RecordImageViewpagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentlist = ArrayList<RecordImageFragment>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: RecordImageFragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size -1)
    }

}