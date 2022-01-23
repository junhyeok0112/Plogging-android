package org.techtown.plogging_android.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentRecordImageBinding


class RecordImageFragment(val url:String) : Fragment() {

    lateinit var binding :FragmentRecordImageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordImageBinding.inflate(inflater , container , false)
        Log.d("setView", url)

        setImage()

        return binding.root
    }

    fun setImage(){
        Log.d("SetView" , "이미지로드 !")
        val imgRef : StorageReference = MyApplication.storage.reference.child(url)
        Glide.with(this)
            .load(imgRef)
            .into(binding.imageSlider)
        binding.imageSliderTv.visibility = View.GONE
    }

}