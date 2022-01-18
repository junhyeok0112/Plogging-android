package org.techtown.plogging_android.Archive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.techtown.plogging_android.databinding.FragmentMyArchiveBinding


class MyArchiveFragment : Fragment() {

    lateinit var binding : FragmentMyArchiveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyArchiveBinding.inflate(inflater , container, false)
        return binding.root
    }

}