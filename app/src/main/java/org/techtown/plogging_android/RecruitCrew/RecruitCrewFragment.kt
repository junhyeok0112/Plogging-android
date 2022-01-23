package com.example.agatha_plog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.plogging_android.Plogging.CompletePloggingFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.RecruitCrew.Adapter.RecruitCrewRecyclerViewAdapter
import org.techtown.plogging_android.RecruitCrew.CrewInfo.CrewInfoFragment
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitResult
import org.techtown.plogging_android.databinding.FragmentRecruitcrewBinding


//모집 리스트 프래그먼트 -> 뷰페이저에 나옴
class RecruitCrewFragment(val crewList : List<RecruitResult>) : Fragment() {

    lateinit var binding: FragmentRecruitcrewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitcrewBinding.inflate(inflater, container , false)
        val recruitCrewRecyclerViewAdapter = RecruitCrewRecyclerViewAdapter(crewList)
        binding.activeCrewRv.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL,false)
        binding.activeCrewRv.adapter = recruitCrewRecyclerViewAdapter

        recruitCrewRecyclerViewAdapter.setMyItemClickListener(object : RecruitCrewRecyclerViewAdapter.MyItemClickListener {    //Adapter가 클릭됐을때 하는 행동정의

            override fun onItemClick(recruitResult: RecruitResult) {
                val crewIdx = recruitResult.crewIdx
                requireActivity().supportFragmentManager.beginTransaction().
                replace(R.id.main_container_fl , CrewInfoFragment().apply {
                    arguments = Bundle().apply {
                        putInt("idx" , crewIdx)
                    }
                })
                    .addToBackStack(null).commitAllowingStateLoss()
            }
        })

        return binding.root
    }


}