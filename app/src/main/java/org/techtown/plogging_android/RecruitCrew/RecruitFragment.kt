package com.example.agatha_plog

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.techtown.plogging_android.MakeCrewFragment
import org.techtown.plogging_android.Mycrew.MyCrewFragment
import org.techtown.plogging_android.Plogging.PloggingFragment
import org.techtown.plogging_android.R
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitCrewResponse
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitResult
import org.techtown.plogging_android.RecruitCrew.Retrofit.RecruitRetrofitInterface
import org.techtown.plogging_android.RecruitCrew.adapter.RecruitViewpagerAdapter
import org.techtown.plogging_android.databinding.FragmentRecruitBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//모집 메인 화면
class RecruitFragment : Fragment() {

    lateinit var binding: FragmentRecruitBinding
    val tabList = arrayOf("전체", "서울", "인천", "대전", "대구", "부산", "울산", "광주", "제주도")
    var arr = ArrayList<List<RecruitResult>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitBinding.inflate(inflater, container, false)
        setListener()
        arr.clear()
        getRecuritCrewAll()
        for (i in 1..8) {
            val mHandler = Handler()
            mHandler.postDelayed(Runnable {
                getRecruitCrewRegion(tabList[i], i)
            }, 300)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //나중에 참고 -> 불러오는동안 어떻게 있다가 시작해 ? ->코루틴...
        val mHandler = Handler()
        mHandler.postDelayed(Runnable {
            setViewpager()
        },5000)
        //setViewpager()
    }

    fun setViewpager() {
        val recruitViewpagerAdapter = RecruitViewpagerAdapter(this, arr)
        binding.recruitVp.adapter = recruitViewpagerAdapter
        binding.recruitVp.offscreenPageLimit = 9
        TabLayoutMediator(binding.recruitTabLayout, binding.recruitVp) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    fun setListener() {
        binding.recruitMakeBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fl, MakeCrewFragment())
                .addToBackStack(null).commit()
        }
    }

    fun getRecruitCrewRegion(region: String, idx: Int) {
        val recruitCrewRegionService = getRetorfit().create(RecruitRetrofitInterface::class.java)

        recruitCrewRegionService.getRecruitCrewRegion(region)
            .enqueue(object : Callback<RecruitCrewResponse> {
                override fun onResponse(
                    call: Call<RecruitCrewResponse>,
                    response: Response<RecruitCrewResponse>
                ) {
                    val resp = response.body()!!
                    arr.add(resp.result)
                }

                override fun onFailure(call: Call<RecruitCrewResponse>, t: Throwable) {
                    Log.d("respons", "실패")
                }
            })
    }

    fun getRecuritCrewAll() {
        val recruitService = getRetorfit().create(RecruitRetrofitInterface::class.java)
        recruitService.getRecruitCrewAll().enqueue(object : Callback<RecruitCrewResponse> {
            override fun onResponse(
                call: Call<RecruitCrewResponse>,
                response: Response<RecruitCrewResponse>
            ) {
                val resp = response.body()!!
                arr.add(resp.result)
            }

            override fun onFailure(call: Call<RecruitCrewResponse>, t: Throwable) {
                Log.d("respons", "실패 All")
            }
        })
    }
}