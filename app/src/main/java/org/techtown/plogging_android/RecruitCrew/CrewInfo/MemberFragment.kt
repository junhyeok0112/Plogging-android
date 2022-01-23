package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.plogging_android.RecruitCrew.Adapter.MemberRVAdapter
import org.techtown.plogging_android.RecruitCrew.Retrofit.CrewMemberResult
import org.techtown.plogging_android.databinding.FragmentMemberBinding

class MemberFragment(val list: List<CrewMemberResult> , val crewIdx:Int) : Fragment() {

    lateinit var binding : FragmentMemberBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemberBinding.inflate(inflater , container, false)

        val memberRVAdapter = MemberRVAdapter(list)
        binding.activeCrewRv.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL,false)
        binding.activeCrewRv.adapter = memberRVAdapter



        return binding.root
    }
}