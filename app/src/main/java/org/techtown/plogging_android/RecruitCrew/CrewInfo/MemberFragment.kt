package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.plogging_android.databinding.FragmentMemberBinding

class MemberFragment : Fragment() {

    lateinit var binding : FragmentMemberBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemberBinding.inflate(inflater , container, false)
        return binding.root
    }
}