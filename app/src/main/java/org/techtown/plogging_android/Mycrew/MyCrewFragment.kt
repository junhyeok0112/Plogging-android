package org.techtown.plogging_android.Mycrew

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import org.techtown.plogging_android.MyApplication
import org.techtown.plogging_android.Mycrew.Adapter.MyCrewViewpagerAdapter
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResponse
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewResult
import org.techtown.plogging_android.Mycrew.Retrofit.MyCrewRetroInterface
import org.techtown.plogging_android.databinding.FragmentMyCrewBinding
import org.techtown.plogging_android.getRetorfit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCrewFragment : Fragment() {

    lateinit var binding : FragmentMyCrewBinding
    val tabList = arrayListOf("활동중" ,"종료됨")
    var active = listOf<MyCrewResult>()
    var death = listOf<MyCrewResult>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCrewBinding.inflate(inflater , container , false)
        getMyCrew("T")
        getMyCrew("F")

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val mHandler = Handler()
        mHandler.postDelayed(Runnable {
            setViewpager()
        },3000)

    }

    fun setViewpager(){
        val myCrewViewpagerAdapter = MyCrewViewpagerAdapter(this,active, death)
        binding.myCrewViewpagerV2.adapter = myCrewViewpagerAdapter
        binding.myCrewViewpagerV2.offscreenPageLimit = 2
        TabLayoutMediator(binding.myCrewTabTl , binding.myCrewViewpagerV2){
                tab,position->
            tab.text = tabList[position]
        }.attach()

    }

    fun getMyCrew(status:String){
        val myCrewService = getRetorfit().create(MyCrewRetroInterface::class.java)
        myCrewService.getMyCrews(MyApplication.jwt!!, status).enqueue(object : Callback<MyCrewResponse>{
            override fun onResponse(
                call: Call<MyCrewResponse>,
                response: Response<MyCrewResponse>
            ) {
                val resp = response.body()!!
                if(status == "T"){
                    active = resp.result
                } else{
                    death = resp.result
                }
            }

            override fun onFailure(call: Call<MyCrewResponse>, t: Throwable) {
                Log.d("mycrew", "tlfvo ${t}")
            }
        })
    }


}