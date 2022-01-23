package org.techtown.plogging_android.Mycrew

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.plogging_android.Mycrew.Adapter.MyCrewRVAdapter
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResult
import org.techtown.plogging_android.R
import org.techtown.plogging_android.RecruitCrew.CrewInfo.CrewInfoFragment
import org.techtown.plogging_android.databinding.FragmentActiveBinding


class ActiveFragment(val list: List<MyCrewResult>) : Fragment() {

    lateinit var binding:FragmentActiveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActiveBinding.inflate(inflater , container ,false)
        val myCrewRVAdapter = MyCrewRVAdapter(list)
        binding.activeCrewRv.layoutManager =  LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL,false)
        binding.activeCrewRv.adapter = myCrewRVAdapter

        myCrewRVAdapter.setMyItemClickListener(object : MyCrewRVAdapter.MyItemClickListener {    //Adapter가 클릭됐을때 하는 행동정의

            override fun onItemClick(mycrewResult: MyCrewResult) {
                val crewIdx = mycrewResult.crewIdx
                requireActivity().supportFragmentManager.beginTransaction().
                replace(R.id.main_container_fl , MyCrewIntroFragment(mycrewResult, crewIdx).apply {
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