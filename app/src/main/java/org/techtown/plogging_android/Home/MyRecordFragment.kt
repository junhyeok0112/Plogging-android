package org.techtown.plogging_android.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.techtown.plogging_android.R
import org.techtown.plogging_android.databinding.FragmentMyRecordBinding


class MyRecordFragment : Fragment() {

    lateinit var binding : FragmentMyRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRecordBinding.inflate(inflater , container ,false)
        return binding.root
    }

}