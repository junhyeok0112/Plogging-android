package org.techtown.plogging_android.RecruitCrew.CrewInfo

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.techtown.plogging_android.RecruitCrew.Retrofit.*
import org.techtown.plogging_android.RecruitCrew.adapter.CrewInfoViewpagerAdapter
import org.techtown.plogging_android.databinding.FragmentCrewinfoBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrewInfoFragment : Fragment() {

    lateinit var binding : FragmentCrewinfoBinding
    val tabList = arrayOf("소개" , "멤버")
    lateinit var crewinfoResult :CrewInfoResult
    lateinit var list :List<CrewMemberResult>
    var crewIdx = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrewinfoBinding.inflate(inflater , container , false)
        crewIdx = arguments?.getInt("idx")!!
        getCrewInfo(crewIdx)
        getCrewMemeber(crewIdx)


        val mHandler = Handler()
        mHandler.postDelayed(Runnable {
            setViewPager()
        },3000)


        return binding.root
    }

    fun setViewPager(){
        val crewInfoViewpagerAdapter = CrewInfoViewpagerAdapter(this , crewinfoResult, list , crewIdx)
        binding.crewinfoFrameVp.adapter = crewInfoViewpagerAdapter
        binding.crewinfoFrameVp.offscreenPageLimit = 2
        TabLayoutMediator(binding.crewinfoTl , binding.crewinfoFrameVp){
                tab,position->
            tab.text = tabList[position]
        }.attach()

    }

    fun getCrewInfo(crewIdx: Int){
        val crewInfoService = getRetorfit().create(CrewInfoRetrofitInterface::class.java)
        crewInfoService.getCrewInfo(crewIdx).enqueue(object : Callback<CrewInfoResponse>{
            override fun onResponse(
                call: Call<CrewInfoResponse>,
                response: Response<CrewInfoResponse>
            ) {
                val resp = response.body()!!
                crewinfoResult = resp.result
            }

            override fun onFailure(call: Call<CrewInfoResponse>, t: Throwable) {

            }
        })
    }

    fun getCrewMemeber(crewIdx : Int){
        val crewMemberService = getRetorfit().create(CrewInfoRetrofitInterface::class.java)
        crewMemberService.getCrewMember(crewIdx).enqueue(object : Callback<CrewMemberResponse>{
            override fun onResponse(
                call: Call<CrewMemberResponse>,
                response: Response<CrewMemberResponse>
            ) {
                val resp = response.body()!!
                list = resp.result
            }

            override fun onFailure(call: Call<CrewMemberResponse>, t: Throwable) {

            }
        })
    }


}