package org.techtown.plogging_android.Plogging.Adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import org.techtown.plogging_android.databinding.FragmentImageBinding

class ImageFragment(val bitmap: Bitmap?): Fragment() {

    lateinit var binding : FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater , container , false)
        if(bitmap != null){
            binding.imageSlider.setImageBitmap(bitmap)
            binding.imageSliderTv.visibility = View.GONE
        }

        return binding.root
    }

    fun setImage(bitmap: Bitmap){
        binding.imageSlider.setImageBitmap(bitmap)
        binding.imageSliderTv.visibility = View.GONE
    }

    fun getImage() : Bitmap{
        val drawable = binding.imageSlider.drawable
        return (drawable as BitmapDrawable).bitmap
    }
}