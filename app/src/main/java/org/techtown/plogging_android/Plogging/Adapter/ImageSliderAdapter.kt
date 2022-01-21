package org.techtown.plogging_android.Plogging.Adapter

import android.graphics.Bitmap
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//ImageFragment만 받게함 -> 어짜피 ImageFragment에서만 적용되는것이므로
class ImageSliderAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentlist = ArrayList<ImageFragment>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment:ImageFragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size -1)
    }

    //프래그먼트를 바꾸는게 아니라 이미지를 변경
    fun changeFragment(bitmap: Bitmap){
        fragmentlist[1].setImage(bitmap)
        Log.d("changeFragment" , "${fragmentlist[1]}")
        notifyDataSetChanged()
    }

    //뷰페이저에있는 이미지들을 배열에 담아서 리턴. -> 이미지 저장할때 사용함
    fun getBitmapList() : ArrayList<Bitmap>{
        val arr = ArrayList<Bitmap>()
        for( fg in fragmentlist ){
            arr.add(fg.getImage())
        }
        return arr
    }

}