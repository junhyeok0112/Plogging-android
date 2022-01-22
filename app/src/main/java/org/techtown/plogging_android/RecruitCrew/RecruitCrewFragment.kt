package com.example.agatha_plog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.plogging_android.databinding.FragmentRecruitcrewBinding


//모집 리스트 프래그먼트 -> 뷰페이저에 나옴
class RecruitCrewFragment : Fragment() {

    lateinit var binding: FragmentRecruitcrewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitcrewBinding.inflate(inflater, container , false)
        return binding.root
    }
}